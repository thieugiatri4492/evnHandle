package tripqm.evn.java.profile.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tripqm.evn.java.profile.entities.Revenue;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {
}
