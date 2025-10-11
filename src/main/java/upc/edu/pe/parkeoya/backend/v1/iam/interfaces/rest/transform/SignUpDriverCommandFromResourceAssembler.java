package upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.transform;


import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands.SignUpDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.iam.interfaces.rest.resources.SignUpDriverResource;

public class SignUpDriverCommandFromResourceAssembler {
    public static SignUpDriverCommand toCommandFromResource(SignUpDriverResource resource) {
        return new SignUpDriverCommand(resource.fullName(), resource.email() ,resource.password(),
                resource.city(), resource.country(), resource.phone(), resource.dni());
    }
}
