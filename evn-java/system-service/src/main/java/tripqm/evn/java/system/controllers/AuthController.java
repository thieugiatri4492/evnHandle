package tripqm.evn.java.system.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.payload.request.LoginRequest;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.payload.response.ApiResponse;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.payload.response.UserResponse;
import tripqm.evn.java.system.service.UserService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequestMapping("/auth")
public class AuthController extends BaseController {
  UserService userService;
    @PostMapping("/login")
    public ApiResponse<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest){
         JwtResponse jwt = userService.loginSystem(loginRequest);
         return ApiResponse.<JwtResponse>builder()
                 .result(jwt)
                 .build();
    }
    @PostMapping("/signup")
    public ApiResponse<UserResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.signupUser(signUpRequest));
        return apiResponse;
    }
}
