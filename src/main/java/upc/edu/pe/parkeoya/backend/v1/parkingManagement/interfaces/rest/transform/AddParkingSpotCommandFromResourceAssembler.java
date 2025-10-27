package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands.AddParkingSpotCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.AddParkingSpotResource;

public class AddParkingSpotCommandFromResourceAssembler {
    public static AddParkingSpotCommand toCommandFromResource(AddParkingSpotResource resource, Long parkingId) {
        return new AddParkingSpotCommand(
                resource.row(),
                resource.column(),
                resource.label(),
                parkingId
        );
    }
}