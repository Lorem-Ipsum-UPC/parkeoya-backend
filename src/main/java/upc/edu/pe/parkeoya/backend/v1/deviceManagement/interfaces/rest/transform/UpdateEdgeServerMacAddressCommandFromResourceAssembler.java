package upc.edu.pe.parkeoya.backend.v1.deviceManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateEdgeServerMacAddressCommand;

public class UpdateEdgeServerMacAddressCommandFromResourceAssembler {
    public static UpdateEdgeServerMacAddressCommand toCommandFromResource(
            Long edgeServerId,
            String newMacAddress) {
        return new UpdateEdgeServerMacAddressCommand(edgeServerId, newMacAddress);
    }
}
