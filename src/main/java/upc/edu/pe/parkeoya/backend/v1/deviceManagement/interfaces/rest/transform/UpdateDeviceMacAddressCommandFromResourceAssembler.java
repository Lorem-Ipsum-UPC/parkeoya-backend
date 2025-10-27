package upc.edu.pe.parkeoya.backend.v1.deviceManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateDeviceMacAddressCommand;

public class UpdateDeviceMacAddressCommandFromResourceAssembler {
    public static UpdateDeviceMacAddressCommand toCommandFromResource(Long deviceId, String newMacAddress) {
        return new UpdateDeviceMacAddressCommand(deviceId, newMacAddress);
    }
}
