package tripqm.evn.java.system.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tripqm.evn.java.system.configuration.AuthenticationRequestInterceptor;
import tripqm.evn.java.system.payload.request.ProfileCreationRequest;
import tripqm.evn.java.system.payload.response.ApiResponse;
import tripqm.evn.java.system.payload.response.UserProfileResponse;

@FeignClient(name = "profile-service", url = "${app.services.profile}",
        configuration = { AuthenticationRequestInterceptor.class })
public interface ProfileClient {
    @PostMapping(value = "/internal/profileUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<UserProfileResponse> createProfile(@RequestBody ProfileCreationRequest request);
}