package ma.fsm.paymentapp.config;

import ma.fsm.paymentapp.entities.Payment;
import ma.fsm.paymentapp.entities.PaymentStatus;
import ma.fsm.paymentapp.entities.PaymentType;
import ma.fsm.paymentapp.entities.Student;
import ma.fsm.paymentapp.repositories.PaymentRepository;
import ma.fsm.paymentapp.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Configuration
public class DemoDataInitializer {
    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        return args -> {
            String storageDirectory = System.getProperty("user.home") + "/inset_data/payments/";
            Path root = Paths.get(storageDirectory);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            List<Student> students = List.of(
                    Student.builder().id(UUID.randomUUID().toString()).firstName("Ahmed").lastName("Alami").code("STD001").email("ahmed.alami@example.com").programId("SDIA").photo("photo1.png").build(),
                    Student.builder().id(UUID.randomUUID().toString()).firstName("Sara").lastName("Benali").code("STD002").email("sara.benali@example.com").programId("SDIA").photo("photo2.png").build(),
                    Student.builder().id(UUID.randomUUID().toString()).firstName("Youssef").lastName("Karim").code("STD003").email("youssef.karim@example.com").programId("GLSID").photo("photo3.png").build(),
                    Student.builder().id(UUID.randomUUID().toString()).firstName("Imane").lastName("Zahraoui").code("STD004").email("imane.zahraoui@example.com").programId("BDCC").photo("photo4.png").build()
            );
            studentRepository.saveAll(students);

            Random random = new Random();
            PaymentType[] types = PaymentType.values();
            PaymentStatus[] statuses = PaymentStatus.values();

            for (Student student : students) {
                for (int i = 1; i <= 10; i++) {
                    double amount = 500 + random.nextInt(7000);
                    String filePath = createDemoReceipt(root, student, i, amount);
                    Payment payment = Payment.builder()
                            .date(LocalDate.now().minusDays(random.nextInt(120)))
                            .amount(amount)
                            .type(types[random.nextInt(types.length)])
                            .status(statuses[random.nextInt(statuses.length)])
                            .file(filePath)
                            .student(student)
                            .build();
                    paymentRepository.save(payment);
                }
            }
        };
    }

    private String createDemoReceipt(Path root, Student student, int index, double amount) throws IOException {
        String filename = student.getCode() + "_payment_" + index + ".pdf";
        Path path = root.resolve(filename);
        if (!Files.exists(path)) {
            String pdf = "%PDF-1.4\n" +
                    "1 0 obj << /Type /Catalog /Pages 2 0 R >> endobj\n" +
                    "2 0 obj << /Type /Pages /Kids [3 0 R] /Count 1 >> endobj\n" +
                    "3 0 obj << /Type /Page /Parent 2 0 R /MediaBox [0 0 612 792] /Contents 4 0 R /Resources << /Font << /F1 5 0 R >> >> >> endobj\n" +
                    "4 0 obj << /Length 120 >> stream\n" +
                    "BT /F1 18 Tf 50 750 Td (Receipt Payment Demo) Tj 0 -30 Td (Student: " + student.getCode() + ") Tj 0 -30 Td (Amount: " + amount + ") Tj ET\n" +
                    "endstream endobj\n" +
                    "5 0 obj << /Type /Font /Subtype /Type1 /BaseFont /Helvetica >> endobj\n" +
                    "trailer << /Root 1 0 R >>\n%%EOF";
            Files.writeString(path, pdf, StandardCharsets.ISO_8859_1);
        }
        return path.toString();
    }
}
