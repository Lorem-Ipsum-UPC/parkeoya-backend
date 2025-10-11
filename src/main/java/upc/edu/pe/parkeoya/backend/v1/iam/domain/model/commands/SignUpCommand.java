package upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands;






import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, List<Role> roles) {
}