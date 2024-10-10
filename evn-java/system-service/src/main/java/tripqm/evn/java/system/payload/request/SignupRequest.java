package tripqm.evn.java.system.payload.request;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tripqm.evn.java.system.customValidator.DobConstraint;
import tripqm.evn.java.system.customValidator.NameConstraint;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "USERNAME_INVALID")
    String userName;

    @NotBlank
    @Size(min = 6, max = 40, message = "PASSWORD_INVALID")
    String password;

    @Size(max = 50)
    @Email
    String email;

    @Size(max = 100)
    @NameConstraint(invalidChar = "<>?`^,.%$#@", message = "NAME_INVALID")
    String firstName;

    @Size(max = 100)
    @NameConstraint(invalidChar = "<>?`^,.%$#@", message = "NAME_INVALID")
    String lastName;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}
