package tripqm.evn.java.system.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.enums.ErrorCode;
import tripqm.evn.java.system.enums.Role;
import tripqm.evn.java.system.exception.ApiException;
import tripqm.evn.java.system.mapper.SUserMapper;
import tripqm.evn.java.system.payload.request.LoginRequest;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.payload.response.UserResponse;
import tripqm.evn.java.system.repository.SUserRepository;
import tripqm.evn.java.system.security.jwt.JwtUtils;
import tripqm.evn.java.system.service.UserService;

import java.util.HashSet;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {
     SUserRepository s_userRepo;
     SUserMapper sUserMapper;
     JwtUtils jwtUtils;

     private String generateToken (LoginRequest loginRequest){
         return  this.jwtUtils.generateNimbusToken(loginRequest.getUserName());
     }
    @Override
    public JwtResponse loginSystem(LoginRequest loginRequest) {
        Optional<S_User> s_user = s_userRepo.findUserByUserName(loginRequest.getUserName());
        PasswordEncoder bcrypt = new BCryptPasswordEncoder(10);
        if(s_user.isPresent()){
            boolean authenticated = bcrypt.matches(loginRequest.getPassword(), s_user.get().getPassword());
            if (authenticated){
                String jwt = this.generateToken(loginRequest);
                return JwtResponse.builder()
                        .token(jwt)
                        .userName(s_user.get().getUserName())
                        .id(s_user.get().getId())
                        .build();
            }else{
                throw  new ApiException(ErrorCode.UNAUTHENTICATED);
            }
        }else{
            throw new ApiException(ErrorCode.USER_NOT_EXISTED);
        }
    }
    public UserResponse signupUser(SignupRequest request){
        log.info("Service: Create User");
        if (s_userRepo.existsByUserName(request.getUserName()))
            throw new ApiException(ErrorCode.USER_EXISTED);
        S_User s_user = sUserMapper.toSUser(request);
        PasswordEncoder bcrypt = new BCryptPasswordEncoder(10);
        s_user.setPassword(bcrypt.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

        s_user.setRoles(roles);

        return sUserMapper.toUserResponse(s_userRepo.save(s_user));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<S_User> s_user = s_userRepo.findUserByUserName(username);
        if (s_user.isPresent()){
            return UserDetailsImpl.build(s_user.get());
        }else
            throw new UsernameNotFoundException("User name not existed");
    }
}
