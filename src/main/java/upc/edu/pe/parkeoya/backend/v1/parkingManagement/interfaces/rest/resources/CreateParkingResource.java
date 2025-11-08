package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources;

public record CreateParkingResource(
        Long ownerId,
        String name,
        String description,
        String address,
        String city,
        String province,
        String postalCode,
        Double lat,
        Double lng,
        Float ratePerHour,
        Float dailyRate,
        Float monthlyRate,
        Integer totalSpots,
        Integer regularSpots,
        Integer disabledSpots,
        Integer electricSpots,
        Integer availableSpots,
        Integer totalRows,
        Integer totalColumns,
        String imageUrl,
        String operatingDays,
        Boolean open24Hours,
        String openingTime,
        String closingTime
) {
}
