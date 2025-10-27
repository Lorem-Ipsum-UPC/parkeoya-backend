package upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.commands.CreatePaymentCommand;
import upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.resources.CreatePaymentResource;

public class CreatePaymentCommandFromResourceAssembler {
    public static CreatePaymentCommand toCommandFromResource(CreatePaymentResource resource) {
        return new CreatePaymentCommand(
                resource.userId(),
                resource.amount(),
                resource.nameOnCard(),
                resource.cardNumber(),
                resource.cardExpiryDate()
        );
    }
}
