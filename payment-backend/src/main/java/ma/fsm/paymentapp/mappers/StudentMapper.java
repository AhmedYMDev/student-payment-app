package ma.fsm.paymentapp.mappers;

import ma.fsm.paymentapp.dtos.StudentDTO;
import ma.fsm.paymentapp.entities.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {
    public StudentDTO toDto(Student student) {
        if (student == null) return null;
        return StudentDTO.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .code(student.getCode())
                .email(student.getEmail())
                .programId(student.getProgramId())
                .photo(student.getPhoto())
                .build();
    }
}
