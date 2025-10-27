package upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.resources;

public record CreatePaymentResource(
        Long userId,
        double amount,
        String nameOnCard,
        String cardNumber,
        String cardExpiryDate
) {
}
