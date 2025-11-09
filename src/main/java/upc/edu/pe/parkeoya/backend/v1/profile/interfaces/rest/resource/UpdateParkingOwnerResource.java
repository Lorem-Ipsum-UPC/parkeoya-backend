package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource;

public record UpdateParkingOwnerResource(
        String fullName,
        String city,
        String country,
        String phone,
        String companyName,
        String ruc
) {
}
