package upc.edu.pe.parkeoya.backend.v1.reservations.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.infrastructure.gateway.ParkingMqttService;
import upc.edu.pe.parkeoya.backend.v1.notifications.application.service.NotificationService;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.model.FcmToken;
import upc.edu.pe.parkeoya.backend.v1.notifications.domain.repository.FcmTokenRepository;
import upc.edu.pe.parkeoya.backend.v1.reservations.application.internal.outboundservices.acl.ExternalParkingService;
import upc.edu.pe.parkeoya.backend.v1.reservations.application.internal.outboundservices.acl.ExternalProfileService;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.aggregates.Reservation;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands.CreateReservationCommand;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands.UpdateReservationStatusCommand;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.services.ReservationCommandService;
import upc.edu.pe.parkeoya.backend.v1.reservations.infrastructure.persistence.jpa.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationCommandServiceImpl implements ReservationCommandService {
    private final ReservationRepository reservationRepository;
    private final ExternalParkingService externalParkingService;
    private final ExternalProfileService externalProfileServiceReservation;
    private final Optional<ParkingMqttService> parkingMqttService;
    private final NotificationService notificationService;
    private final FcmTokenRepository fcmTokenRepository;

    public ReservationCommandServiceImpl(ReservationRepository reservationRepository, ExternalParkingService externalParkingService, ExternalProfileService externalProfileServiceReservation, Optional<ParkingMqttService> parkingMqttService,
                                          NotificationService notificationService, FcmTokenRepository fcmTokenRepository) {
        this.reservationRepository = reservationRepository;
        this.externalParkingService = externalParkingService;
        this.externalProfileServiceReservation = externalProfileServiceReservation;
        this.notificationService = notificationService;
        this.fcmTokenRepository = fcmTokenRepository;
        this.parkingMqttService = parkingMqttService;
    }

    @Override
    public Optional<Reservation> handle(CreateReservationCommand command) throws IOException {
        var driverFullName = externalProfileServiceReservation.getDriverFullNameByUserId(command.driverId());
        var parkingSpotLabel = externalParkingService.getSpotLabel(command.parkingSpotId(), command.parkingId());
        var parkingRatePerHour = externalParkingService.getParkingRatePerHour(command.parkingId());
        if (parkingRatePerHour == null) {
            return Optional.empty();
        }
        var reservation = new Reservation(command, driverFullName, parkingRatePerHour, parkingSpotLabel);
        var savedReservation = reservationRepository.save(reservation);
        externalParkingService.updateParkingSpotAvailability(command.parkingId(), command.parkingSpotId(), "RESERVED");
        externalParkingService.updateAvailableSpotsCount(command.parkingId(), 1, "subtract");

        // Enviar notificación al propietario
        Long ownerUserId = externalParkingService.getOwnerUserIdByParkingId(command.parkingId());
        List<FcmToken> tokens = fcmTokenRepository.findAllByUserId(ownerUserId);
        for (FcmToken token : tokens) {
            notificationService.sendNotification(
                    token.getToken(),
                    "Nueva reserva",
                    "Han reservado un espacio en tu estacionamiento."
            );
        }

        // Solo enviar mensaje MQTT si el servicio está disponible
        parkingMqttService.ifPresent(mqtt -> {
            try {
                mqtt.reserveSpot(String.valueOf(command.parkingSpotId()), true);
            } catch (IOException e) {
                System.err.println("Error al enviar mensaje MQTT: " + e.getMessage());
            }
        });
        return Optional.of(savedReservation);
    }

    @Override
    public Optional<Reservation> handle(UpdateReservationStatusCommand command) throws IOException {
        var reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.updateStatus(command.status());
        var spotId = UUID.fromString(reservation.getParkingSpotId());
        var updatedReservation = reservationRepository.save(reservation);
        if (command.status().equalsIgnoreCase("CONFIRMED")) {
            // Solo enviar mensaje MQTT si el servicio está disponible
            parkingMqttService.ifPresent(mqtt -> {
                try {
                    mqtt.reserveSpot(String.valueOf(reservation.getParkingSpotId()), false);
                } catch (IOException e) {
                    System.err.println("Error al enviar mensaje MQTT: " + e.getMessage());
                }
            });
        } else if (command.status().equalsIgnoreCase("CANCELED")) {
            externalParkingService.updateParkingSpotAvailability(reservation.getParkingId(), spotId, "AVAILABLE");
            // Solo enviar mensaje MQTT si el servicio está disponible
            parkingMqttService.ifPresent(mqtt -> {
                try {
                    mqtt.reserveSpot(String.valueOf(reservation.getParkingSpotId()), false);
                } catch (IOException e) {
                    System.err.println("Error al enviar mensaje MQTT: " + e.getMessage());
                }
            });
            //externalParkingService.updateAvailableSpotsCount(reservation.getParkingId(), 1, "add");
        } else if (command.status().equalsIgnoreCase("COMPLETED")) {
            externalParkingService.updateParkingSpotAvailability(reservation.getParkingId(), spotId, "AVAILABLE");
            //externalParkingService.updateAvailableSpotsCount(reservation.getParkingId(), 1, "add");
        }
        // Enviar notificación al propietario
        Long ownerUserId = externalParkingService.getOwnerUserIdByParkingId(reservation.getParkingId());
        List<FcmToken> tokens = fcmTokenRepository.findAllByUserId(ownerUserId);
        for (FcmToken token : tokens) {
            notificationService.sendNotification(
                    token.getToken(),
                    "Estado de reserva actualizado",
                    "El estado de la reserva ha sido actualizado a: " + command.status()
            );
        }
        return Optional.of(updatedReservation);
    }
}
