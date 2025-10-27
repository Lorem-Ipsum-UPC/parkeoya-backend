package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands;

public record CreateParkingCommand(
        Long ownerId,
        String name,
        String description,
        String address,
        Double lat,
        Double lng,
        Float ratePerHour,
        Integer totalSpots,
        Integer availableSpots,
        Integer totalRows,
        Integer totalColumns,
        String imageUrl
) {
}
