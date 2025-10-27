package upc.edu.pe.parkeoya.backend.v1.payment.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.aggregates.ReservationPayment;
import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.commands.CreatePaymentCommand;
import upc.edu.pe.parkeoya.backend.v1.payment.domain.services.PaymentCommandService;
import upc.edu.pe.parkeoya.backend.v1.payment.infrastructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {
    private final PaymentRepository paymentRepository;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }


    @Override
    public Optional<ReservationPayment> handleReservationPayment(CreatePaymentCommand command, Long reservationId) {
        var reservationPayment = new ReservationPayment(command, reservationId);
        var createdPayment = paymentRepository.save(reservationPayment);
        return Optional.of(createdPayment);
    }

}
