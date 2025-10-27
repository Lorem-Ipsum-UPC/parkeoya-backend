package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands;

import java.util.UUID;

public record CreateDeviceCommand(
        Long parkingId, UUID parkingSpotId, String spotStatus, String spotLabel, String edgeServerId
) {
}
