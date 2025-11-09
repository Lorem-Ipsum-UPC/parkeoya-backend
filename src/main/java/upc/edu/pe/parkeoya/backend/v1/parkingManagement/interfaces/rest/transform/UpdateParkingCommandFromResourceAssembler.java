package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands.UpdateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.UpdateParkingResource;

public class UpdateParkingCommandFromResourceAssembler {

    public static UpdateParkingCommand toCommandFromResource(UpdateParkingResource resource, Long parkingId) {
        return new UpdateParkingCommand(
                parkingId,
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
                resource.open24hours(),
                resource.openingTime(),
                resource.closingTime(),
                resource.operatingDays(),
                resource.imageUrl(),
                resource.totalSpots(),
                resource.regularSpots(),
                resource.disabledSpots(),
                resource.electricSpots()
        );
    }
}
