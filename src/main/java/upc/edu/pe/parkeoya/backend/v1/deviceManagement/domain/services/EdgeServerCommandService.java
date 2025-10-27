package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.services;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.EdgeServer;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.CreateEdgeServerCommand;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.commands.UpdateEdgeServerMacAddressCommand;

import java.util.Optional;

public interface EdgeServerCommandService {
    Optional<EdgeServer> handle(CreateEdgeServerCommand command);
    Optional<EdgeServer> handle(UpdateEdgeServerMacAddressCommand command);
}
