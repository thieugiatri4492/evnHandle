package tripqm.evn.java.power.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tripqm.evn.java.power.entity.PowerSellData;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PowerDataResultDto {
    private Integer totalRecords;
    private List<PowerSellData> resultSet;
}
