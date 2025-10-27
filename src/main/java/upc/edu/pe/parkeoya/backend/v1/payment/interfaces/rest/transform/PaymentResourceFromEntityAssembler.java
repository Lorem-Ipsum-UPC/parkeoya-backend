package upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.aggregates.ReservationPayment;
import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.valueobjects.Payment;
import upc.edu.pe.parkeoya.backend.v1.payment.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment payment) {
        if (payment instanceof ReservationPayment) {
            ReservationPayment reservationPayment = (ReservationPayment) payment;
            return new PaymentResource(
                    "ReservationPayment",
                    reservationPayment.getId(),
                    reservationPayment.getAmount(),
                    reservationPayment.getPaidAt().toString(),
                    reservationPayment.getReservationId(),
                    null
            );
        }
        return null;
    }
}
