package upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.CreateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.resources.CreateParkingResource;

public class CreateParkingCommandFromResourceAssembler {
    public static CreateParkingCommand toCommandFromResource(CreateParkingResource resource) {
        return new CreateParkingCommand(
                resource.ownerId(),
                resource.name(),
                resource.description(),
                resource.address(),
                resource.ratePerHour(),
                resource.totalSpots(),
                resource.availableSpots(),
                resource.totalRows(),
                resource.totalColumns(),
                resource.imageUrl()
        );
    }
}
