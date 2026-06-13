package ma.fsm.paymentapp.repositories;

import ma.fsm.paymentapp.entities.Payment;
import ma.fsm.paymentapp.entities.PaymentStatus;
import ma.fsm.paymentapp.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByType(PaymentType type);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByStudent_Code(String code);
    List<Payment> findByStudent_ProgramId(String programId);
}
