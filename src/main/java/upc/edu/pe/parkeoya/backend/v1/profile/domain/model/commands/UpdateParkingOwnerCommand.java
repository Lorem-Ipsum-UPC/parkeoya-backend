package upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands;

public record UpdateParkingOwnerCommand(
        Long parkingOwnerId,
        String fullName,
        String city,
        String country,
        String phone,
        String companyName,
        String ruc
) {
}
