package tripqm.evn.java.power.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PowerDataRequest {
    String madviqly;
    int thang;
    int nam;
    int loaiDulieu;
    int soKy;
    int loaiKh;
}
