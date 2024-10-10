package tripqm.evn.java.system.enums;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(1000, "Undefined error!", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_EXISTED(1001, "Username not found or incorrect!", HttpStatus.NOT_FOUND),
    USER_EXISTED(1002, "Username already existed!", HttpStatus.CONFLICT),
    UNAUTHENTICATED(1003, "Unauthenticated!", HttpStatus.FORBIDDEN),
    UNAUTHORIZED(1004, "You does not have permission!", HttpStatus.UNAUTHORIZED),
    USERNAME_INVALID(1005, "Username must be at least 3 character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1006, "Password must be at least 6 character", HttpStatus.BAD_REQUEST),
    NAME_INVALID(1007, "Name cannot contains special characters like {invalidChar}", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1008, "Not a valid date of birth", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
