package tripqm.evn.java.system.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.exception.ApiException;
import tripqm.evn.java.system.payload.request.LoginRequest;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.repository.SUserRepository;

@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private SUserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    // Input given
    private LoginRequest loginRequest;

    // Output expected
    private JwtResponse jwtResponse;
    private S_User s_user;

    @BeforeEach
    void initData() {
        loginRequest =
                LoginRequest.builder().userName("tripqm.it").password("123456").build();

        jwtResponse = JwtResponse.builder()
                .token("cf0600f538b3")
                .id(1L)
                .userName("tripqm.it")
                .build();

        s_user =
                S_User.builder().id(1L).userName("tripqm.it").password("123456").build();
    }

    @Test
    void login_validRequest_success() {
        // GIVEN
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.ofNullable(s_user));
        when(passwordEncoder.matches(
                        loginRequest.getPassword(),
                        Optional.ofNullable(s_user).get().getPassword()))
                .thenReturn(true);

        // WHEN
        var response = userService.loginSystem(loginRequest);
        // THEN

        Assertions.assertThat(response.getId()).isEqualTo(1);
        Assertions.assertThat(response.getUserName()).isEqualTo("tripqm.it");
    }

    @Test
    void login_invalidRequest_fail() {
        // GIVEN
        when(userRepository.findUserByUserName(anyString())).thenReturn(Optional.ofNullable(null));
        // WHEN
        var exception = assertThrows(ApiException.class, () -> userService.loginSystem(loginRequest));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
}
