package tripqm.evn.java.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tripqm.evn.java.system.domain.S_Permission;

@Repository
public interface SPermissionRepository extends JpaRepository<S_Permission, String> {}
