package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands;

public record UpdateParkingCommand(
        Long parkingId,
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
        Boolean open24hours,
        String openingTime,
        String closingTime,
        String operatingDays,
        String imageUrl,
        Integer totalSpots,
        Integer regularSpots,
        Integer disabledSpots,
        Integer electricSpots
) {
}
