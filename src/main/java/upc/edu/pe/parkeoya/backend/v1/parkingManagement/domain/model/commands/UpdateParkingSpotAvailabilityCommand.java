package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands;

import java.util.UUID;

public record UpdateParkingSpotAvailabilityCommand(Long parkingId, UUID parkingSpotId, String availability) {
}
