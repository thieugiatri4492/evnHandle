package tripqm.evn.java.profile.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import tripqm.evn.java.profile.dtos.response.ApiResponse;
import tripqm.evn.java.profile.dtos.response.RevenueResponse;
import tripqm.evn.java.profile.service.UserProfileService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    UserProfileService userProfileService;

    @GetMapping("/revenue")
    ApiResponse<List<RevenueResponse>> getAllProfiles() {
        return ApiResponse.<List<RevenueResponse>>builder()
                .result(userProfileService.getAllRevenue())
                .build();
    }
}
