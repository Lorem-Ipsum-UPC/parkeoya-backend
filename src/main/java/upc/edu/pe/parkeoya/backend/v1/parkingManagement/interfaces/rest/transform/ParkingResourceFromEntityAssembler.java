package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.ParkingResource;

public class ParkingResourceFromEntityAssembler {
    public static ParkingResource toResourceFromEntity(Parking entity) {
        return new ParkingResource(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getLat(),
                entity.getLng(),
                entity.getRatePerHour(),
                entity.getAverageRating(),
                entity.getRatingCount(),
                entity.getTotalSpots(),
                entity.getAvailableSpots(),
                entity.getTotalRows(),
                entity.getTotalColumns(),
                entity.getImageUrl()
        );
    }
}
