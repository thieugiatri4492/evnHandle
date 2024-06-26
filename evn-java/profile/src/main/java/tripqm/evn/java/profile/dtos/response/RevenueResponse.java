package tripqm.evn.java.profile.dtos.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevenueResponse {
    String revenueMonth;
    Integer revenueValue;
}
