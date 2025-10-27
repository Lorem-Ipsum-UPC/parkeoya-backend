package upc.edu.pe.parkeoya.backend.v1.payment.domain.model.aggregates;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.commands.CreatePaymentCommand;
import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.valueobjects.Payment;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("RESERVATION")
@NoArgsConstructor
@Getter
public class ReservationPayment extends Payment {

    @Column(name = "reservation_id")
    private Long reservationId;

    public ReservationPayment(CreatePaymentCommand command, Long reservationId) {
        super(command);
        this.reservationId = reservationId;
    }


    @Override
    public boolean isForReservation() {
        return true;
    }
}