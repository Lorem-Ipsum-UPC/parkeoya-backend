package upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands;

import java.time.LocalDate;
import java.util.UUID;

public record CreateReservationCommand(
        Long driverId,
        String vehiclePlate,
        Long parkingId,
        UUID parkingSpotId,
        LocalDate date,
        String startTime,
        String endTime
) {
}
