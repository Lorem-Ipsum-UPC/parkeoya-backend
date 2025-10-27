package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands;

public record CreateEdgeServerCommand(
        String serverId,
        String apiKey,
        String name,
        String macAddress,
        String status,
        Long parkingId
        ) {

}
