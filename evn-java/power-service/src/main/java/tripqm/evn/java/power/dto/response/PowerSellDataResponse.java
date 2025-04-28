package tripqm.evn.java.power.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PowerSellDataResponse {
    String maDviqly;
    String maKhang;
    String tenKhang;
    String maNhomKhang;
    String congSuat;
    String thuongPham;
    String rowNum;
}
