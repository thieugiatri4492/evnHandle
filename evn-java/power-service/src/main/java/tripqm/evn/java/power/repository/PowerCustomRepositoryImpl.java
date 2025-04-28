package tripqm.evn.java.power.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;
import tripqm.evn.java.power.dto.PowerDataResultDto;
import tripqm.evn.java.power.entity.PowerSellData;

import java.util.List;

@Repository
public class PowerCustomRepositoryImpl implements PowerCustomRepository{
    private final EntityManager entityManager;

    public PowerCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public PowerDataResultDto getPowerData(String madviqly, int thang, int nam, int loaiDulieu, int soKy, int loaiKh, int page, int size) {
        StoredProcedureQuery query = entityManager.createNamedStoredProcedureQuery("PowerSellData.getPowerData");

        // Set procedure parameters
        query.setParameter("P_MADVIQLY", madviqly);
        query.setParameter("P_THANG", thang);
        query.setParameter("P_NAM", nam);
        query.setParameter("P_LOAIDULIEU", loaiDulieu);
        query.setParameter("P_SOKY", soKy);
        query.setParameter("P_LOAIKH", loaiKh);
        query.setParameter("P_PAGE_NUMBER", page);
        query.setParameter("P_PAGE_SIZE", size);

        query.execute();

        // Retrieve OUT parameter
        Integer totalRecords = (Integer) query.getOutputParameterValue("P_TOTAL_RECORDS");

        // Retrieve REF_CURSOR result set
        @SuppressWarnings("unchecked")
        List<PowerSellData> resultSet = query.getResultList();

        return new PowerDataResultDto(totalRecords, resultSet);
    }
}
