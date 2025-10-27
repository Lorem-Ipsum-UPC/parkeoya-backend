package upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.resources;

public record PaymentResource(
        String paymentType,
        Long id,
        double amount,
        String paidAt,
        Long reservationId,
        Long subscriptionId
) {
}