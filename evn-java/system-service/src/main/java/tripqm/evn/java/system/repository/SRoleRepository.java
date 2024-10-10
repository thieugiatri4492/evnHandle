package tripqm.evn.java.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tripqm.evn.java.system.domain.S_Role;

@Repository
public interface SRoleRepository extends JpaRepository<S_Role, String> {}
