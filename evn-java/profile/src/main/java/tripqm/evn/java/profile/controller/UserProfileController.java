package tripqm.evn.java.profile.controller;

import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import tripqm.evn.java.profile.dtos.request.ProfileCreationRequest;
import tripqm.evn.java.profile.dtos.response.ApiResponse;
import tripqm.evn.java.profile.dtos.response.UserProfileResponse;
import tripqm.evn.java.profile.service.ProfileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    ProfileService profileService;
    @GetMapping("/profileUsers/{profileId}")
    UserProfileResponse getProfile(@PathVariable String profileId) {
        return profileService.getProfile(profileId);
    }

    @GetMapping("/profileUsers")
    ApiResponse<List<UserProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<UserProfileResponse>>builder()
                .result(profileService.getAllProfiles())
                .build();
    }

    @GetMapping("/profileUsers/my-profile")
    ApiResponse<UserProfileResponse> getMyProfile() {
        return ApiResponse.<UserProfileResponse>builder()
                .result(profileService.getMyProfile())
                .build();
    }
}
