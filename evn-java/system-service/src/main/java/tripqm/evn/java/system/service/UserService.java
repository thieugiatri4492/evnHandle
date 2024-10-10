package tripqm.evn.java.system.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nimbusds.jose.JOSEException;

import tripqm.evn.java.system.payload.request.*;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.payload.response.UserResponse;

public interface UserService extends UserDetailsService {
    JwtResponse loginSystem(LoginRequest loginRequest);

    JwtResponse refreshToken(RefreshTokenRequest tokenRequest) throws ParseException, JOSEException;

    void logoutSystem(LogoutRequest request) throws JOSEException, ParseException;

    UserResponse signupUser(SignupRequest request);

    UserResponse getUserInfo();

    @PreAuthorize("hasRole('ADMIN')")
    // @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    List<UserResponse> getUsers();
    // Use PostAuthorize when to check the change of data like username
    @PostAuthorize("returnObject.username == authentication.name")
    UserResponse getUser(Long userId);

    @PostAuthorize("returnObject.username == authentication.name")
    UserResponse updateUser(Long userId, UserUpdateRequest request);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(Long userId);
}
