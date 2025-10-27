package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources;

public record CreateParkingResource(
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
