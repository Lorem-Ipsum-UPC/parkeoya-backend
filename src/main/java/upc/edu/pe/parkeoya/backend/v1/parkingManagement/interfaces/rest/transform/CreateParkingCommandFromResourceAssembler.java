package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands.CreateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.CreateParkingResource;

public class CreateParkingCommandFromResourceAssembler {
    public static CreateParkingCommand toCommandFromResource(CreateParkingResource resource) {
        return new CreateParkingCommand(
                resource.ownerId(),
                resource.name(),
                resource.description(),
                resource.address(),
                resource.city(),
                resource.province(),
                resource.postalCode(),
                resource.lat(),
                resource.lng(),
                resource.ratePerHour(),
                resource.dailyRate(),
                resource.monthlyRate(),
                resource.totalSpots(),
                resource.regularSpots(),
                resource.disabledSpots(),
                resource.electricSpots(),
                resource.availableSpots(),
                resource.totalRows(),
                resource.totalColumns(),
                resource.imageUrl(),
                resource.operatingDays(),
                resource.open24Hours(),
                resource.openingTime(),
                resource.closingTime()
        );
    }
}
