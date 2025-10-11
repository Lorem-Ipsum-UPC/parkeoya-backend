package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource;

public record DriverResource(
        Long userId,
        Long driverId,
        String fullName,
        String city,
        String country,
        String phone,
        String dni
) {
}
