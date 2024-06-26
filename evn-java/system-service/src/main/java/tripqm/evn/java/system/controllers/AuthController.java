package tripqm.evn.java.system.controllers;

import java.text.ParseException;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import tripqm.evn.java.system.payload.request.LoginRequest;
import tripqm.evn.java.system.payload.request.LogoutRequest;
import tripqm.evn.java.system.payload.request.RefreshTokenRequest;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.payload.response.ApiResponse;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.payload.response.UserResponse;
import tripqm.evn.java.system.service.UserService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/auth")
public class AuthController {
    UserService userService;

    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwt = userService.loginSystem(loginRequest);
        return ApiResponse.<JwtResponse>builder().result(jwt).build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        userService.logoutSystem(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/signup")
    public ApiResponse<UserResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.signupUser(signUpRequest));
        return apiResponse;
    }

    @PostMapping("/refreshToken")
    ApiResponse<JwtResponse> authenticate(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        var result = userService.refreshToken(request);
        return ApiResponse.<JwtResponse>builder().result(result).build();
    }
}
