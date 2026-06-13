package ma.fsm.paymentapp.services.impl;

import lombok.RequiredArgsConstructor;
import ma.fsm.paymentapp.dtos.PaymentDTO;
import ma.fsm.paymentapp.entities.Payment;
import ma.fsm.paymentapp.entities.PaymentStatus;
import ma.fsm.paymentapp.entities.PaymentType;
import ma.fsm.paymentapp.entities.Student;
import ma.fsm.paymentapp.exceptions.ResourceNotFoundException;
import ma.fsm.paymentapp.mappers.PaymentMapper;
import ma.fsm.paymentapp.repositories.PaymentRepository;
import ma.fsm.paymentapp.repositories.StudentRepository;
import ma.fsm.paymentapp.services.PaymentService;
import ma.fsm.paymentapp.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final PaymentMapper paymentMapper;
    private final StorageService storageService;

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPayments() {
        return paymentRepository.findAll().stream().map(paymentMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentDTO getPayment(Long id) {
        return paymentMapper.toDto(findPayment(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsByStudentCode(String studentCode) {
        return paymentRepository.findByStudent_Code(studentCode).stream().map(paymentMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsByProgramId(String programId) {
        return paymentRepository.findByStudent_ProgramId(programId).stream().map(paymentMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status).stream().map(paymentMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentDTO> getPaymentsByType(PaymentType type) {
        return paymentRepository.findByType(type).stream().map(paymentMapper::toDto).toList();
    }

    @Override
    public PaymentDTO savePayment(LocalDate date, double amount, PaymentType type, String studentCode, MultipartFile file) throws IOException {
        Student student = studentRepository.findByCode(studentCode)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with code: " + studentCode));
        String pdfPath = storageService.store(file);
        Payment payment = Payment.builder()
                .date(date == null ? LocalDate.now() : date)
                .amount(amount)
                .type(type)
                .status(PaymentStatus.CREATED)
                .file(pdfPath)
                .student(student)
                .build();
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    public PaymentDTO updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = findPayment(id);
        payment.setStatus(status);
        return paymentMapper.toDto(paymentRepository.save(payment));
    }

    @Override
    @Transactional(readOnly = true)
    public Resource loadPaymentFile(Long id) throws IOException {
        Payment payment = findPayment(id);
        return storageService.loadAsResource(payment.getFile());
    }

    private Payment findPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }
}
