package tripqm.evn.java.system.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.payload.request.LoginRequest;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.payload.response.UserResponse;

public interface UserService extends UserDetailsService {
    JwtResponse loginSystem (LoginRequest loginRequest);
    UserResponse signupUser(SignupRequest request);
}
