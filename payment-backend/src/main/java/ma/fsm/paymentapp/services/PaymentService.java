package ma.fsm.paymentapp.services;

import ma.fsm.paymentapp.dtos.PaymentDTO;
import ma.fsm.paymentapp.entities.PaymentStatus;
import ma.fsm.paymentapp.entities.PaymentType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getPayments();
    PaymentDTO getPayment(Long id);
    List<PaymentDTO> getPaymentsByStudentCode(String studentCode);
    List<PaymentDTO> getPaymentsByProgramId(String programId);
    List<PaymentDTO> getPaymentsByStatus(PaymentStatus status);
    List<PaymentDTO> getPaymentsByType(PaymentType type);
    PaymentDTO savePayment(LocalDate date, double amount, PaymentType type, String studentCode, MultipartFile file) throws IOException;
    PaymentDTO updatePaymentStatus(Long id, PaymentStatus status);
    Resource loadPaymentFile(Long id) throws IOException;
}
