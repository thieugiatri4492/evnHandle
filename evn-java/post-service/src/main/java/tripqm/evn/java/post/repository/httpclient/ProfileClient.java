package tripqm.evn.java.post.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tripqm.evn.java.post.dto.response.ApiResponse;
import tripqm.evn.java.post.dto.response.UserProfileResponse;

@FeignClient(name = "profile-service", url = "${api.services.profile.url}")
public interface ProfileClient {
    @GetMapping("/internal/profileUsers/{userId}")
    ApiResponse<UserProfileResponse> getProfile(@PathVariable String userId);
}
