package ma.fsm.paymentapp.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.fsm.paymentapp.dtos.PaymentDTO;
import ma.fsm.paymentapp.dtos.PaymentStatusUpdateRequest;
import ma.fsm.paymentapp.entities.PaymentStatus;
import ma.fsm.paymentapp.entities.PaymentType;
import ma.fsm.paymentapp.services.PaymentService;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PaymentRestController {

    private final PaymentService paymentService;

    @GetMapping
    public List<PaymentDTO> getPayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/{id}")
    public PaymentDTO getPayment(@PathVariable Long id) {
        return paymentService.getPayment(id);
    }

    @GetMapping("/student/{studentCode}")
    public List<PaymentDTO> getPaymentsByStudentCode(@PathVariable String studentCode) {
        return paymentService.getPaymentsByStudentCode(studentCode);
    }

    @GetMapping("/program/{programId}")
    public List<PaymentDTO> getPaymentsByProgram(@PathVariable String programId) {
        return paymentService.getPaymentsByProgramId(programId);
    }

    @GetMapping("/status/{status}")
    public List<PaymentDTO> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        return paymentService.getPaymentsByStatus(status);
    }

    @GetMapping("/type/{type}")
    public List<PaymentDTO> getPaymentsByType(@PathVariable PaymentType type) {
        return paymentService.getPaymentsByType(type);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public PaymentDTO savePayment(
            @RequestParam String studentCode,
            @RequestParam double amount,
            @RequestParam PaymentType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        return paymentService.savePayment(
                date,
                amount,
                type,
                studentCode,
                file
        );
    }

    @PatchMapping("/{id}/status")
    public PaymentDTO updatePaymentStatus(
            @PathVariable Long id,
            @Valid @RequestBody PaymentStatusUpdateRequest request
    ) {
        return paymentService.updatePaymentStatus(
                id,
                request.status()
        );
    }

    @GetMapping(
            value = "/{id}/file",
            produces = MediaType.APPLICATION_PDF_VALUE
    )
    public ResponseEntity<Resource> getPaymentFile(
            @PathVariable Long id
    ) throws IOException {

        Resource pdf = paymentService.loadPaymentFile(id);

        String filename =
                pdf.getFilename() == null
                        ? "receipt.pdf"
                        : pdf.getFilename();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\""
                )
                .body(pdf);
    }
}