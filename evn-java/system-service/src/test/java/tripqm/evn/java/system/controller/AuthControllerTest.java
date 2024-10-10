package tripqm.evn.java.system.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.system.payload.request.LoginRequest;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.service.UserService;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Input given
    private LoginRequest loginRequest;

    // Output expected
    private JwtResponse jwtResponse;

    // Init data for input and output
    @BeforeEach
    void initData() {
        loginRequest =
                LoginRequest.builder().userName("tripqm.it").password("123456").build();

        jwtResponse = JwtResponse.builder()
                .token("cf0600f538b3")
                .id(1L)
                .userName("tripqm.it")
                .build();
    }

    @Test
    void login_validRequest_success() throws Exception {
        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(loginRequest);
        Mockito.when(userService.loginSystem(ArgumentMatchers.any())).thenReturn(jwtResponse);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("result.token").value("cf0600f538b3"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.userName").value("tripqm.it"));
    }

    @Test
    void login_invalidRequest_fail() throws Exception {
        // Given
        loginRequest.setUserName("ab");
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(loginRequest);

        // WHEN,THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1005))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Username must be at least 3 character"));
    }
}
