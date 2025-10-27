package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands;

public record UpdateDeviceMacAddressCommand(
        Long deviceId,
        String newMacAddress
) {
}
