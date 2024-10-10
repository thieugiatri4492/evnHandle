package tripqm.evn.java.system.configuration;

import java.util.HashSet;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.system.domain.S_Permission;
import tripqm.evn.java.system.domain.S_Role;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.enums.PredefinedRole;
import tripqm.evn.java.system.mapper.ProfileMapper;
import tripqm.evn.java.system.payload.request.SignupRequest;
import tripqm.evn.java.system.repository.SPermissionRepository;
import tripqm.evn.java.system.repository.SRoleRepository;
import tripqm.evn.java.system.repository.SUserRepository;
import tripqm.evn.java.system.repository.httpclient.ProfileClient;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Profile("dev")
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    ProfileClient profileClient;
    ProfileMapper profileMapper;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    /*@ConditionalOnProperty(
    prefix = "spring",
    value = "jpa.properties.hibernate.dialect",
    havingValue = "org.hibernate.dialect.PostgreSQLDialect")*/
    ApplicationRunner applicationRunner(
            SUserRepository userRepository,
            SRoleRepository roleRepository,
            SPermissionRepository permissionRepository) {
        return args -> {
            if (userRepository.findUserByUserName(ADMIN_USER_NAME).isEmpty()) {
                log.info("Init admin user.....");
                S_Permission permission = permissionRepository.save(S_Permission.builder()
                        .name("FullControl")
                        .description("Full control for admin")
                        .build());
                var permissions = new HashSet<S_Permission>();
                permissions.add(permission);

                S_Role adminRole = roleRepository.save(S_Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .permissions(permissions)
                        .build());
                var roles = new HashSet<S_Role>();
                roles.add(adminRole);

                permissions.clear();
                permission = permissionRepository.save(S_Permission.builder()
                        .name("Basic")
                        .description("Basic control for user")
                        .build());
                permissions.add(permission);
                roleRepository.save(S_Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .permissions(permissions)
                        .build());
              String passwordEncode = passwordEncoder.encode(ADMIN_PASSWORD);
                S_User user = S_User.builder()
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncode)
                        .roles(roles)
                        .build();
                userRepository.save(user);
                var createProfileAdmin = SignupRequest.builder()
                        .firstName("Admin")
                        .lastName("Administrator")
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncode)
                        .email("handleevn@me.com")
                        .city("TriPQM city")
                        .build();
                //Create profile via profile service
                var profileRequest = profileMapper.toProfileCreationRequest(createProfileAdmin);
                profileRequest.setUserId(user.getId());
                profileClient.createProfile(profileRequest);
                log.warn("Create default user admin with password admin!");
            }else{
                log.info("Admin user already existed.....");
            }
        };
    }
}
