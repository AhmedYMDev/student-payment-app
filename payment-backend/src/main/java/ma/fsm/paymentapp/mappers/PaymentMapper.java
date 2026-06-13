package ma.fsm.paymentapp.mappers;

import ma.fsm.paymentapp.dtos.PaymentDTO;
import ma.fsm.paymentapp.entities.Payment;
import ma.fsm.paymentapp.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDTO toDto(Payment payment) {
        if (payment == null) return null;
        Student student = payment.getStudent();
        return PaymentDTO.builder()
                .id(payment.getId())
                .date(payment.getDate())
                .amount(payment.getAmount())
                .type(payment.getType())
                .status(payment.getStatus())
                .file(payment.getFile())
                .studentCode(student != null ? student.getCode() : null)
                .studentFullName(student != null ? student.getFirstName() + " " + student.getLastName() : null)
                .studentProgramId(student != null ? student.getProgramId() : null)
                .build();
    }
}
