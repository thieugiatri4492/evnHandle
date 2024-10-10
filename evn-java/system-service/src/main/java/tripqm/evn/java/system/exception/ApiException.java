package tripqm.evn.java.system.exception;

import lombok.*;
import tripqm.evn.java.system.enums.ErrorCode;

@Getter
@Setter
public class ApiException extends RuntimeException {
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
