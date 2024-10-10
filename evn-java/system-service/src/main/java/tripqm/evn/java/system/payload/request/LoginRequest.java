package tripqm.evn.java.system.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    @NotBlank
    String userName;

    @Size(min = 3, message = "PASSWORD_INVALID")
    @NotBlank
    String password;
}
