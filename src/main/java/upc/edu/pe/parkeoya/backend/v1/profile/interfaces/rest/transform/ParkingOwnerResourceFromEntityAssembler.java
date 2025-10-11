package upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.transform;


import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.ParkingOwner;
import upc.edu.pe.parkeoya.backend.v1.profile.interfaces.rest.resource.ParkingOwnerResource;

public class ParkingOwnerResourceFromEntityAssembler {
    public static ParkingOwnerResource toResourceFromEntity(ParkingOwner parkingOwner) {
        return new ParkingOwnerResource(
                parkingOwner.getUserId(), parkingOwner.getId(), parkingOwner.getFullName(), parkingOwner.getCity(),
                parkingOwner.getCountry(), parkingOwner.getPhone(), parkingOwner.getCompanyName(), parkingOwner.getRuc()
        );
    }
}
