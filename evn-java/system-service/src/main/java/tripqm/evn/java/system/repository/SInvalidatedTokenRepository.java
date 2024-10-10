package tripqm.evn.java.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tripqm.evn.java.system.domain.S_InvalidToken;

@Repository
public interface SInvalidatedTokenRepository extends JpaRepository<S_InvalidToken, String> {}
