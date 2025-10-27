package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.queries;

public record GetParkingsByOwnerIdQuery(Long ownerId) {
    public GetParkingsByOwnerIdQuery {
        if (ownerId == null) {
            throw new IllegalArgumentException("Owner ID cannot be null");
        }
    }
}
