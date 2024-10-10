package tripqm.evn.java.profile.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import tripqm.evn.java.profile.dtos.request.ProfileCreationRequest;
import tripqm.evn.java.profile.dtos.response.ApiResponse;
import tripqm.evn.java.profile.dtos.response.UserProfileResponse;
import tripqm.evn.java.profile.service.ProfileService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileInternalController {
    ProfileService profileService;

    @PostMapping("/internal/profileUsers")
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request) {
        return ApiResponse.<UserProfileResponse>builder()
                .result(profileService.createProfile(request))
                .build();
    }
    @GetMapping("/internal/profileUsers/{userId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId) {
        Long userIdAsLong = Long.parseLong(userId);
        return ApiResponse.<UserProfileResponse>builder()
                .result(profileService.getByUserId(userIdAsLong))
                .build();
    }

}
