package ma.fsm.paymentapp.repositories;

import ma.fsm.paymentapp.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByCode(String code);
    List<Student> findByProgramId(String programId);

    @Query("""
            select s from Student s
            where lower(s.firstName) like lower(concat('%', :keyword, '%'))
               or lower(s.lastName) like lower(concat('%', :keyword, '%'))
               or lower(s.code) like lower(concat('%', :keyword, '%'))
               or lower(s.programId) like lower(concat('%', :keyword, '%'))
            """)
    List<Student> search(@Param("keyword") String keyword);
}
