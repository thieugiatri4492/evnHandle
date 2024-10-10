package tripqm.evn.java.system.payload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ResultObjectDto<T> extends BaseResultDto {
    private T data;
}
