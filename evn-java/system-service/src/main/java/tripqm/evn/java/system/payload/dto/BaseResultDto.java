package tripqm.evn.java.system.payload.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import tripqm.evn.java.system.enums.ResultStatus;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class BaseResultDto {
    private ResultStatus status;

    @Builder.Default
    private String message = "Executed success";
}
