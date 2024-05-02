package tripqm.evn.java.system.domain;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "S_User")
public class S_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name="USER_NAME")
    @NonNull
    String userName;

    @Column(name="PASSWORD")
    @NonNull
    String password;

    @Column(name="FULL_NAME")
    @NonNull
    String fullName;

    @Column(name="EMAIL")
    @NonNull
    String email;

    Set<String> roles;

}
