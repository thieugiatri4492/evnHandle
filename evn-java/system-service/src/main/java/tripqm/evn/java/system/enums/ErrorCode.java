package tripqm.evn.java.system.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(500,"Undefined error!"),
    USER_NOT_EXISTED(404,"Username not found or incorrect!"),
    USER_EXISTED(401,"Username already existed!"),
    UNAUTHENTICATED(403,"Unauthenticated!"),
    USERNAME_INVALID(1001,"Username must be at least 3 character"),
    PASSWORD_INVALID(1002,"Password must be at least 3 character")
    ;
    private int code;
    private String message;

}
