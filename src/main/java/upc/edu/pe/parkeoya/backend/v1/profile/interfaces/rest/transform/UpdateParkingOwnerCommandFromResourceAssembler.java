package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.UpdateParkingOwnerCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.UpdateParkingOwnerResource;

public class UpdateParkingOwnerCommandFromResourceAssembler {

    public static UpdateParkingOwnerCommand toCommandFromResource(UpdateParkingOwnerResource resource, Long parkingOwnerId) {
        return new UpdateParkingOwnerCommand(
                parkingOwnerId,
                resource.fullName(),
                resource.city(),
                resource.country(),
                resource.phone(),
                resource.companyName(),
                resource.ruc()
        );
    }
}
