package upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.transform;


import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.entities.Role;
import upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}