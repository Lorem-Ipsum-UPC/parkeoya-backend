package upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands;

public record CreateParkingCommand(
        Long ownerId,
        String name,
        String description,
        String address,
        Float ratePerHour,
        Integer totalSpots,
        Integer availableSpots,
        Integer totalRows,
        Integer totalColumns,
        String imageUrl
) {
}
