package upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.resources;

public record SignUpDriverResource(
        String email,
        String password,
        String fullName,
        String city,
        String country,
        String phone,
        String dni
) {
}
