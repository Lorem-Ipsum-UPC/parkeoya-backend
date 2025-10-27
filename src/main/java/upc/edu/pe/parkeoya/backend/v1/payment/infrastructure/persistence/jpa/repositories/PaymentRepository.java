package upc.edu.pe.parkeoya.backend.v1.payment.infrastructure.persistence.jpa.repositories;

import upc.edu.pe.parkeoya.backend.v1.payment.domain.model.valueobjects.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
