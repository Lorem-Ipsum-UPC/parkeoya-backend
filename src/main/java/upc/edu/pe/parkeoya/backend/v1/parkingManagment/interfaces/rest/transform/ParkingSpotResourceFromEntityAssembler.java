package upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.resources.ParkingSpotResource;

public class ParkingSpotResourceFromEntityAssembler {
    public static ParkingSpotResource toResourceFromEntity(ParkingSpot entity) {
        return new ParkingSpotResource(
                entity.getId(),
                entity.getParkingId(),
                entity.getAvailable(),
                entity.getRowIndex(),
                entity.getColumnIndex(),
                entity.getLabel()
        );
    }
}
