package tripqm.evn.java.system.domain;

import java.util.Set;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "S_User")
public class S_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "USER_NAME")
    @NonNull
    String userName;

    @Column(name = "PASSWORD")
    @NonNull
    String password;

    @ManyToMany
    @ToString.Exclude
    Set<S_Role> roles;
}
