package tripqm.evn.java.system.payload.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@RequiredArgsConstructor
public class MessageResponse implements Serializable {
    private static final long serialVersionUID = -6448620768602438445L;
    @NonNull
    private String message;
    private final transient Object obj;
    private int status;
    private int subCode;
    private String timestamp;

}
