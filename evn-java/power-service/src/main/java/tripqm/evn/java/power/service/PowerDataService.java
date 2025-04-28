package tripqm.evn.java.power.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.power.dto.PowerDataResultDto;
import tripqm.evn.java.power.dto.response.PaginationResponse;
import tripqm.evn.java.power.dto.response.PowerSellDataResponse;
import tripqm.evn.java.power.entity.PowerSellData;
import tripqm.evn.java.power.mapper.PowerDataMapper;
import tripqm.evn.java.power.repository.PowerRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PowerDataService {
    PowerRepository powerRepository;
    PowerDataMapper powerDataMapper;

    @Transactional(readOnly = true)
    public PaginationResponse<PowerSellDataResponse> getSellPowerData(
            String madviqly, int thang, int nam, int loaiDulieu, int soKy, int loaiKh, int page, int size) {
        //List<PowerSellData>
        PowerDataResultDto sellPowerData = null;
        int totalRecords = 0;
        int totalPages = 0;
        try {
            sellPowerData =
                    powerRepository.getPowerData(madviqly, thang, nam, loaiDulieu, soKy, loaiKh, page, size);
                    //.get("P_RST");
            totalPages =sellPowerData.getTotalRecords() / size;
        } catch (Exception e) {
            e.printStackTrace(); // Prints full stack trace
            throw new RuntimeException("Error fetching data from repository: " + e.getMessage(), e);
        }
        //List<Object> listPowerData = Arrays.asList(sellPowerData);
        var result = sellPowerData.getResultSet().stream()
                .map(powerDataMapper::toPowerSellDataResponse)
                .toList();
        return PaginationResponse.<PowerSellDataResponse>builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(totalPages)
                .totalElements(totalRecords)
                .data(result)
                .build();
    }
}
