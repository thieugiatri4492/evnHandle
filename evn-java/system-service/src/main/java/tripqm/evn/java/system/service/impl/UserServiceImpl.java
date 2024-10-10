package tripqm.evn.java.system.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.system.domain.S_InvalidToken;
import tripqm.evn.java.system.domain.S_Role;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.enums.ErrorCode;
import tripqm.evn.java.system.enums.PredefinedRole;
import tripqm.evn.java.event.dto.NotificationEvent;
import tripqm.evn.java.system.exception.ApiException;
import tripqm.evn.java.system.mapper.ProfileMapper;
import tripqm.evn.java.system.mapper.SUserMapper;
import tripqm.evn.java.system.payload.request.*;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.payload.response.UserResponse;
import tripqm.evn.java.system.repository.SInvalidatedTokenRepository;
import tripqm.evn.java.system.repository.SRoleRepository;
import tripqm.evn.java.system.repository.SUserRepository;
import tripqm.evn.java.system.repository.httpclient.ProfileClient;
import tripqm.evn.java.system.security.jwt.JwtUtils;
import tripqm.evn.java.system.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
    SUserRepository s_userRepo;
    SRoleRepository roleRepository;
    SInvalidatedTokenRepository invalidatedTokenRepository;
    SUserMapper sUserMapper;
    ProfileMapper profileMapper;
    JwtUtils jwtUtils;
    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    KafkaTemplate<String, Object> kafkaTemplate;

    private String generateToken(S_User user) {
        return this.jwtUtils.generateNimbusToken(user);
    }

    @Override
    public JwtResponse loginSystem(LoginRequest loginRequest) {
        Optional<S_User> s_user = s_userRepo.findUserByUserName(loginRequest.getUserName());
        if (s_user.isPresent()) {
            boolean authenticated = passwordEncoder.matches(
                    loginRequest.getPassword(), s_user.get().getPassword());
            if (authenticated) {
                String jwt = this.generateToken(s_user.get());
                return JwtResponse.builder()
                        .token(jwt)
                        .userName(s_user.get().getUserName())
                        .id(s_user.get().getId())
                        .build();
            } else {
                throw new ApiException(ErrorCode.UNAUTHENTICATED);
            }
        } else {
            throw new ApiException(ErrorCode.USER_NOT_EXISTED);
        }
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest tokenRequest) throws ParseException, JOSEException {
        return this.jwtUtils.refreshNimbusToken(tokenRequest);
    }

    @Override
    public void logoutSystem(LogoutRequest request) throws JOSEException, ParseException {
        try {
            var signToken = this.jwtUtils.verifyNimbusToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            S_InvalidToken invalidatedToken =
                    S_InvalidToken.builder().id(jit).expiryTime(expiryTime).build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (ApiException exception) {
            log.info("Token already expired");
        }
    }

    public UserResponse signupUser(SignupRequest request) {
        log.info("Service: Create User");
        if (Boolean.TRUE.equals(s_userRepo.existsByUserName(request.getUserName()))) throw new ApiException(ErrorCode.USER_EXISTED);
        S_User s_user = sUserMapper.toSUser(request);
        s_user.setPassword(passwordEncoder.encode(request.getPassword()));
        //Create profile via profile service
        var profileRequest = profileMapper.toProfileCreationRequest(request);

        HashSet<S_Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        s_user.setRoles(roles);
        s_user = s_userRepo.save(s_user);
        profileRequest.setUserId(s_user.getId());
        profileClient.createProfile((profileRequest));

        //Publish message to kafka
        NotificationEvent notificationEvent = NotificationEvent.builder()
                                                .channel("EMAIL")
                                                .recipient(request.getEmail())
                                                .subject("Welcome to evnHandle")
                                                .body("Hello, " + request.getUserName())
                                                .build();
        kafkaTemplate.send("notification-delivery", notificationEvent);
        return sUserMapper.toUserResponse(s_user);
    }

    @Override
    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return s_userRepo.findAll().stream().map(sUserMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse getUser(Long id) {
        log.info("In method get User by id");
        return sUserMapper.toUserResponse(
                s_userRepo.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_EXISTED)));
    }

    @Override
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        S_User user = s_userRepo.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_EXISTED));

        sUserMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return sUserMapper.toUserResponse(s_userRepo.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        s_userRepo.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<S_User> s_user = s_userRepo.findUserByUserName(username);
        if (s_user.isPresent()) {
            return UserDetailsImpl.build(s_user.get());
        } else throw new UsernameNotFoundException("User name not existed");
    }

    @Override
    public UserResponse getUserInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        S_User user =
                s_userRepo.findUserByUserName(name).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_EXISTED));

        return sUserMapper.toUserResponse(user);
    }
}
