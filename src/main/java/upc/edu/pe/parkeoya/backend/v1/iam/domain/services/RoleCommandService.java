package upc.edu.pe.parkeoya.backend.v1.iam.domain.services;

import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
