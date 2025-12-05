package upc.edu.pe.parkeoya.backend.v1.deviceManagement.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.Device;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.CreateDeviceCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateDeviceCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateDeviceMacAddressCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.services.DeviceCommandService;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.infrastructure.gateway.ParkingMqttService;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.infrastructure.persistence.jpa.repositories.DeviceRepository;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.acl.ParkingSpotContextFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class DeviceCommandServiceImpl implements DeviceCommandService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceCommandServiceImpl.class);

    private final DeviceRepository deviceRepository;
    private final ParkingMqttService parkingMqttService;
    private final ParkingSpotContextFacade parkingSpotContextFacade;

    public DeviceCommandServiceImpl(DeviceRepository deviceRepository, @Lazy ParkingMqttService parkingMqttService, @Lazy ParkingSpotContextFacade parkingSpotContextFacade) {
        this.deviceRepository = deviceRepository;
        this.parkingMqttService = parkingMqttService;
        this.parkingSpotContextFacade = parkingSpotContextFacade;
    }

    @Override
    public Optional<Device> handle(CreateDeviceCommand command) {
        Device device = new Device(command.parkingId(), command.parkingSpotId(), command.spotStatus(), command.spotLabel(), command.edgeServerId());
        return Optional.of(deviceRepository.save(device));
    }

    @Override
    public Optional<Device> handle(UpdateDeviceCommand command) {
        logger.info("=== INICIO UpdateDeviceCommand ===");
        logger.info("DeviceId (spotId): {}", command.deviceId());
        logger.info("SpotStatus recibido: {}", command.spotStatus());
        logger.info("EdgeId: {}", command.edgeServerId());
        logger.info("MacAddress: {}", command.macAddress());
        logger.info("Type: {}", command.type());
        
        var device = deviceRepository.findByParkingSpotId_SpotId(command.deviceId())
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));
        
        logger.info("Device encontrado - ID: {}, ParkingId: {}", device.getId(), device.getParkingId());
        logger.info("Estado ANTES: device.spotStatus={}", device.getSpotStatus());
        
        device.updateMissingFields(command);
        var updatedDevice = deviceRepository.save(device);
        
        logger.info("Estado DESPUES de save: device.spotStatus={}", updatedDevice.getSpotStatus());
        
        // Actualizar también el estado del parking spot
        if (command.spotStatus() != null) {
            logger.info("Llamando a updateParkingSpotAvailability con:");
            logger.info("  - parkingId: {}", updatedDevice.getParkingId());
            logger.info("  - parkingSpotId: {}", command.deviceId());
            logger.info("  - availability: {}", command.spotStatus());
            
            try {
                parkingSpotContextFacade.updateParkingSpotAvailability(
                    updatedDevice.getParkingId(), 
                    command.deviceId(), 
                    command.spotStatus()
                );
                logger.info("✅ updateParkingSpotAvailability ejecutado exitosamente");
            } catch (Exception e) {
                logger.error("❌ ERROR al actualizar parking spot: {}", e.getMessage(), e);
            }
        } else {
            logger.warn("⚠️ spotStatus es NULL, no se actualiza parking_spots");
        }
        
        logger.info("=== FIN UpdateDeviceCommand ===");
        return Optional.of(updatedDevice);
    }

    @Override
    public Optional<Device> handle(UpdateDeviceMacAddressCommand command) throws IOException {
        var device = deviceRepository.findById(command.deviceId())
                .orElseThrow(() -> new IllegalArgumentException("Device not found"));
        device.setMacAddress(command.newMacAddress());
        var updatedDevice = deviceRepository.save(device);
        parkingMqttService.sendDeviceListByEdgeServerId(updatedDevice.getEdgeServerId());
        return Optional.of(updatedDevice);
    }
}
