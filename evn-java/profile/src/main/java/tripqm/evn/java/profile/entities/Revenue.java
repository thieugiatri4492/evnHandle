package tripqm.evn.java.profile.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "revenue")
public class Revenue {
    @Id
    @Column(name = "revenue_month")
    String revenueMonth;

    @Column(name = "revenue")
    Integer revenueValue;
}
