package ma.fsm.paymentapp.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import ma.fsm.paymentapp.dtos.StudentDTO;
import ma.fsm.paymentapp.services.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDTO> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/program/{programId}")
    public List<StudentDTO> getStudentsByProgram(@PathVariable String programId) {
        return studentService.getStudentsByProgramId(programId);
    }

    @GetMapping("/search")
    public List<StudentDTO> searchStudents(@RequestParam(defaultValue = "") String keyword) {
        return studentService.searchStudents(keyword);
    }

    @GetMapping("/{code}")
    public StudentDTO getStudentByCode(@PathVariable String code) {
        return studentService.getStudentByCode(code);
    }
}