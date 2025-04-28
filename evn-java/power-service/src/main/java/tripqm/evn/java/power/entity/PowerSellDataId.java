package tripqm.evn.java.power.entity;

import java.io.Serializable;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PowerSellDataId implements Serializable {
    String maDviqly;
    String maKhang;
}
