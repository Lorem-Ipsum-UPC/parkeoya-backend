package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands;

public record UpdateParkingRatingCountCommand(Long parkingId, Float ratingCount) {
}
