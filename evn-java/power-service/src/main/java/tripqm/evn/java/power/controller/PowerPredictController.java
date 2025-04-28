package tripqm.evn.java.power.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.power.dto.response.ApiResponse;
import tripqm.evn.java.power.dto.response.PaginationResponse;
import tripqm.evn.java.power.dto.response.PowerSellDataResponse;
import tripqm.evn.java.power.service.PowerDataService;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PowerPredictController {
    PowerDataService powerDataService;

    @GetMapping("/getSellPowerData")
    ApiResponse<PaginationResponse<PowerSellDataResponse>> getSellPowerData(
            @RequestParam(value = "madviqly") String madviqly,
            @RequestParam(value = "thang") int thang,
            @RequestParam(value = "nam") int nam,
            @RequestParam(value = "loaiDuLieu") int loaiDuLieu,
            @RequestParam(value = "soKy") int soKy,
            @RequestParam(value = "loaiKH") int loaiKH,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return ApiResponse.<PaginationResponse<PowerSellDataResponse>>builder()
                .result(powerDataService.getSellPowerData(madviqly, thang, nam, loaiDuLieu, soKy, loaiKH, page, size))
                .build();
    }
}
