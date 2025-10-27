package upc.edu.pe.parkeoya.backend.v1.deviceManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateDeviceCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.interfaces.rest.resources.UpdateDeviceResource;

import java.util.UUID;

public class UpdateDeviceCommandFromResourceAssembler {
    public static UpdateDeviceCommand toCommandFromResource(UpdateDeviceResource resource, UUID deviceId) {
        return new UpdateDeviceCommand(
                deviceId,
                resource.edgeId(),
                resource.macAddress(),
                resource.type()
        );
    }
}
