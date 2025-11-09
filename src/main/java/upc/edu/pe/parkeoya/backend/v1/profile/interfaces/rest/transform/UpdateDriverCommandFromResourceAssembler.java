package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.UpdateDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.UpdateDriverResource;

public class UpdateDriverCommandFromResourceAssembler {

    public static UpdateDriverCommand toCommandFromResource(UpdateDriverResource resource, Long driverId) {
        return new UpdateDriverCommand(
                driverId,
                resource.fullName(),
                resource.city(),
                resource.country(),
                resource.phone(),
                resource.dni()
        );
    }
}
