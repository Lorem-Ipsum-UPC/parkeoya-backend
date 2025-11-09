package upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands;

public record UpdateDriverCommand(
        Long driverId,
        String fullName,
        String city,
        String country,
        String phone,
        String dni
) {
}
