package upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(
    Long id,
    String email,
    List<String> roles
) {
}
