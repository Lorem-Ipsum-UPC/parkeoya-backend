package upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.resources;

import java.util.UUID;

public record ParkingSpotResource(
        UUID id,
        Long parkingId,
        Boolean available,
        Integer rowIndex,
        Integer columnIndex,
        String label
) {
}