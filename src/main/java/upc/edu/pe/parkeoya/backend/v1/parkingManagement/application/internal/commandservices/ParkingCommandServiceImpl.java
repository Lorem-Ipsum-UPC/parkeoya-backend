package upc.edu.pe.parkeoya.backend.v1.parkingManagement.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.EdgeServer;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.infrastructure.persistence.jpa.repositories.EdgeServerRepository;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.application.internal.outboundservices.acl.ExternalDeviceService;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands.*;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.services.ParkingCommandService;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.infrastructure.persistence.jpa.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingCommandServiceImpl implements ParkingCommandService {

    private final ParkingRepository parkingRepository;
    private final EdgeServerRepository edgeServerRepository;
    private final ExternalDeviceService externalDeviceService;

    public ParkingCommandServiceImpl(ParkingRepository parkingRepository, EdgeServerRepository edgeServerRepository, ExternalDeviceService externalDeviceService) {
        this.parkingRepository = parkingRepository;
        this.edgeServerRepository = edgeServerRepository;
        this.externalDeviceService = externalDeviceService;
    }

    @Override
    public Optional<Parking> handle(CreateParkingCommand command) {
        var parking = new Parking(command);
        
        // Auto-generate parking spots based on totalRows and totalColumns
        if (command.totalRows() != null && command.totalColumns() != null && 
            command.totalRows() > 0 && command.totalColumns() > 0) {
            
            int spotCounter = 1;
            for (int row = 1; row <= command.totalRows(); row++) {
                for (int col = 1; col <= command.totalColumns(); col++) {
                    String label = String.valueOf(spotCounter);
                    parking.addParkingSpot(new AddParkingSpotCommand(row, col, label, parking.getId()));
                    spotCounter++;
                }
            }
        }
        
        var createdParking = parkingRepository.save(parking);
        
        // Create Edge Server for the parking
        EdgeServer edgeServer = new EdgeServer(createdParking.getId());
        edgeServerRepository.save(edgeServer);
        
        // Create devices for each auto-generated spot
        var spots = createdParking.getParkingSpots();
        if (!spots.isEmpty()) {
            for (var spot : spots) {
                externalDeviceService.createDevice(
                    createdParking.getId(), 
                    spot.getId(), 
                    spot.getStatus(), 
                    spot.getLabel(), 
                    edgeServer.getServerId()
                );
            }
        }
        
        return Optional.of(createdParking);
    }

    @Override
    public Optional<ParkingSpot> handle(AddParkingSpotCommand command) {
        var parking = this.parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        var spot = parking.addParkingSpot(command);
        var updatedParking = parkingRepository.save(parking);

        var edgeServer = edgeServerRepository.findByParkingId_ParkingId(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Edge Server not found for parking"));

        externalDeviceService.createDevice(command.parkingId(), spot.getId(), spot.getStatus(), spot.getLabel(), edgeServer.getServerId());

        return updatedParking.getParkingSpots().stream()
                .filter(parkingSpot -> parkingSpot.getParkingId().equals(command.parkingId()))
                .findFirst();
    }

    @Override
    public Optional<String> handle(UpdateParkingSpotAvailabilityCommand command) {
        System.out.println("\n========================================");
        System.out.println("🔄 ACTUALIZANDO PARKING SPOT");
        System.out.println("ParkingId: " + command.parkingId());
        System.out.println("ParkingSpotId: " + command.parkingSpotId());
        System.out.println("Nuevo estado: " + command.availability());
        System.out.println("========================================\n");
        
        var parking = this.parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        System.out.println("✅ Parking encontrado: " + parking.getName());
        
        var parkingSpot = parking.getParkingSpot(command.parkingSpotId());

        if (parkingSpot == null) {
            System.out.println("❌ ERROR: Parking spot NO encontrado");
            throw new IllegalArgumentException("Parking spot not found");
        }

        System.out.println("✅ ParkingSpot encontrado - Label: " + parkingSpot.getLabel());
        System.out.println("   Estado ANTES: " + parkingSpot.getStatus());
        
        parkingSpot.updateStatus(command.availability());
        
        System.out.println("   Estado DESPUES: " + parkingSpot.getStatus());

        var savedParking = parkingRepository.save(parking);
        System.out.println("✅ Parking guardado en BD");
        System.out.println("========================================\n");

        return Optional.of("Parking spot with ID " + command.parkingSpotId() + " availability updated to " + command.availability());
    }

    @Override
    public Optional<String> handle(UpdateAvailableParkingSpotCountCommand command) {
        var parking = this.parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        parking.updateAvailableSpotsCount(command.numberAvailable(), command.operation());

        parkingRepository.save(parking);

        var newAvailableCount = parking.getAvailableSpots();

        return Optional.of("Available parking spots count updated to " + newAvailableCount);
    }

    @Override
    public Optional<String> handle(UpdateParkingRatingCommand command) {
        var parking = this.parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        parking.setRating(command.rating());

        var updatedParking = parkingRepository.save(parking);

        return Optional.of("Parking rating updated to " + updatedParking.getRating());
    }

    @Override
    public Optional<Parking> handle(UpdateParkingCommand command) {
        var parking = this.parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        if (command.name() != null) parking.setName(command.name());
        if (command.description() != null) parking.setDescription(command.description());
        if (command.address() != null) parking.setAddress(command.address());
        if (command.city() != null) parking.setCity(command.city());
        if (command.province() != null) parking.setProvince(command.province());
        if (command.postalCode() != null) parking.setPostalCode(command.postalCode());
        if (command.lat() != null) parking.setLat(command.lat());
        if (command.lng() != null) parking.setLng(command.lng());
        if (command.ratePerHour() != null) parking.setRatePerHour(command.ratePerHour());
        if (command.dailyRate() != null) parking.setDailyRate(command.dailyRate());
        if (command.monthlyRate() != null) parking.setMonthlyRate(command.monthlyRate());
        if (command.open24hours() != null) parking.setOpen24Hours(command.open24hours());
        if (command.openingTime() != null) parking.setOpeningTime(command.openingTime());
        if (command.closingTime() != null) parking.setClosingTime(command.closingTime());
        if (command.operatingDays() != null) parking.setOperatingDays(command.operatingDays());
        if (command.imageUrl() != null) parking.setImageUrl(command.imageUrl());
        if (command.totalSpots() != null) parking.setTotalSpots(command.totalSpots());
        if (command.regularSpots() != null) parking.setRegularSpots(command.regularSpots());
        if (command.disabledSpots() != null) parking.setDisabledSpots(command.disabledSpots());
        if (command.electricSpots() != null) parking.setElectricSpots(command.electricSpots());

        var updated = parkingRepository.save(parking);
        return Optional.of(updated);
    }
}
