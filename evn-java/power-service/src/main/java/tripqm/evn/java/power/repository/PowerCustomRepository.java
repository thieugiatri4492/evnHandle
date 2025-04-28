package tripqm.evn.java.power.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import tripqm.evn.java.power.dto.PowerDataResultDto;
import tripqm.evn.java.power.entity.PowerSellData;

public interface PowerCustomRepository {
   PowerDataResultDto getPowerData( String madviqly, int thang, int nam, int loaiDulieu, int soKy, int loaiKh,
                                  int page, int size);
}
