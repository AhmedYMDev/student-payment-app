package ma.fsm.paymentapp.services;

import ma.fsm.paymentapp.dtos.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getStudents();
    List<StudentDTO> getStudentsByProgramId(String programId);
    StudentDTO getStudentByCode(String code);
    List<StudentDTO> searchStudents(String keyword);
}
