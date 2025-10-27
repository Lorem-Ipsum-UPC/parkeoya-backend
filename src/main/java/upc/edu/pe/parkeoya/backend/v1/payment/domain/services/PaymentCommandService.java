package upc.edu.pe.parkeoya.backend.v1.payment.domain.services;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.aggregates.ReservationPayment;
import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.commands.CreatePaymentCommand;

import java.util.Optional;

public interface PaymentCommandService {

    Optional<ReservationPayment> handleReservationPayment(CreatePaymentCommand command,
                                                           Long reservationId);
}