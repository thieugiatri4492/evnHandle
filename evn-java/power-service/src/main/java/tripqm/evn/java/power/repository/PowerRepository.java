package tripqm.evn.java.power.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tripqm.evn.java.power.entity.PowerSellData;

@Repository
public interface PowerRepository extends JpaRepository<PowerSellData, Long> ,PowerCustomRepository{
   /* @Procedure(name = "PowerSellData.getPowerData")
    // List<PowerSellData> getPowerData
    Map<String,Object> getPowerData(
            @Param("P_MADVIQLY") String madviqly,
            @Param("P_THANG") int thang,
            @Param("P_NAM") int nam,
            @Param("P_LOAIDULIEU") int loaiDulieu,
            @Param("P_SOKY") int soKy,
            @Param("P_LOAIKH") int loaiKh,
            @Param("P_PAGE_NUMBER") int page,
            @Param("P_PAGE_SIZE") int size);*/
}
