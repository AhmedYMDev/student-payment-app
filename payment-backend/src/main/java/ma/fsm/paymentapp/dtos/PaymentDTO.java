package ma.fsm.paymentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.fsm.paymentapp.entities.PaymentStatus;
import ma.fsm.paymentapp.entities.PaymentType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String file;
    private String studentCode;
    private String studentFullName;
    private String studentProgramId;
}
