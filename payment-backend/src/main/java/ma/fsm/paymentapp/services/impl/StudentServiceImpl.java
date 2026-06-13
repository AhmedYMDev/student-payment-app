package ma.fsm.paymentapp.services.impl;

import lombok.RequiredArgsConstructor;
import ma.fsm.paymentapp.dtos.StudentDTO;
import ma.fsm.paymentapp.exceptions.ResourceNotFoundException;
import ma.fsm.paymentapp.mappers.StudentMapper;
import ma.fsm.paymentapp.repositories.StudentRepository;
import ma.fsm.paymentapp.services.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<StudentDTO> getStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toDto).toList();
    }

    @Override
    public List<StudentDTO> getStudentsByProgramId(String programId) {
        return studentRepository.findByProgramId(programId).stream().map(studentMapper::toDto).toList();
    }

    @Override
    public StudentDTO getStudentByCode(String code) {
        return studentRepository.findByCode(code)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with code: " + code));
    }

    @Override
    public List<StudentDTO> searchStudents(String keyword) {
        if (keyword == null || keyword.isBlank()) return getStudents();
        return studentRepository.search(keyword).stream().map(studentMapper::toDto).toList();
    }
}
