package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.services;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.Device;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.CreateDeviceCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateDeviceCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateDeviceMacAddressCommand;

import java.io.IOException;
import java.util.Optional;

public interface DeviceCommandService {
    Optional<Device> handle(CreateDeviceCommand command);
    Optional<Device> handle(UpdateDeviceCommand command);
    Optional<Device> handle(UpdateDeviceMacAddressCommand command) throws IOException;
}
