package upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.queries;

public record GetAllReservationsByParkingIdAndStatusQuery(Long parkingId, String status) {
    public GetAllReservationsByParkingIdAndStatusQuery {
        if (parkingId == null || status == null || status.isBlank()) {
            throw new IllegalArgumentException("Parking ID and status must not be null or empty");
        }
        if (parkingId <= 0) {
            throw new IllegalArgumentException("Parking ID must be a positive number");
        }
    }
}
