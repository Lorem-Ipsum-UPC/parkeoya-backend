package upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands.SignUpParkingOwnerCommand;
import upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.resources.SignUpParkingOwnerResource;

public class SignUpParkingOwnerCommandFromResourceAssembler {
    public static SignUpParkingOwnerCommand toCommandFromResource(SignUpParkingOwnerResource resource) {
        return new SignUpParkingOwnerCommand(resource.fullName(), resource.email() ,resource.password(),
                resource.city(), resource.country(), resource.phone(), resource.companyName(),resource.ruc());
    }
}
