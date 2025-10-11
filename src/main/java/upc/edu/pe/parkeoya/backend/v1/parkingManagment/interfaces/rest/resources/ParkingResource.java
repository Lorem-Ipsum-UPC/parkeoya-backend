package upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.resources;

public record ParkingResource(
        Long id,
        Long ownerId,
        String name,
        String description,
        String address,
        Float ratePerHour,
        Float rating,
        Integer totalSpots,
        Integer availableSpots,
        Integer totalRows,
        Integer totalColumns,
        String imageUrl
) {
}