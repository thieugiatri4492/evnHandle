package tripqm.evn.java.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tripqm.evn.java.system.model.S_User;

public interface SUserRepository extends JpaRepository<S_User,Long> {
    S_User findUserByUserName(String userName);
    Boolean existsByUserName(String userName);
}
