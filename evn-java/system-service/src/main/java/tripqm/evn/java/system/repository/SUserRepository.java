package tripqm.evn.java.system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tripqm.evn.java.system.domain.S_User;

@Repository
public interface SUserRepository extends JpaRepository<S_User, Long> {
    Optional<S_User> findUserByUserName(String userName);

    Boolean existsByUserName(String userName);
}
