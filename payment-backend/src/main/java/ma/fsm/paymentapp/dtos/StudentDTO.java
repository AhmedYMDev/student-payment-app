package ma.fsm.paymentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String code;
    private String email;
    private String programId;
    private String photo;
}
