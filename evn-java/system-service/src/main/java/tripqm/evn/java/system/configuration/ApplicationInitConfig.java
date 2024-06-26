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
import tripqm.evn.java.system.domain.S_Role;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.enums.PredefinedRole;
import tripqm.evn.java.system.repository.SRoleRepository;
import tripqm.evn.java.system.repository.SUserRepository;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Profile("dev")
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    /*@ConditionalOnProperty(
    prefix = "spring",
    value = "jpa.properties.hibernate.dialect",
    havingValue = "org.hibernate.dialect.PostgreSQLDialect")*/
    ApplicationRunner applicationRunner(SUserRepository userRepository, SRoleRepository roleRepository) {
        return args -> {
            log.info("Init admin user.....");
            if (userRepository.findUserByUserName(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(S_Role.builder()
                        .name(PredefinedRole.USER_ROLE)
                        .description("User role")
                        .build());
                S_Role adminRole = roleRepository.save(S_Role.builder()
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("Admin role")
                        .build());
                var roles = new HashSet<S_Role>();
                roles.add(adminRole);

                S_User user = S_User.builder()
                        .userName(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .fullName("ADMIN")
                        .email("admin@.evnspc.vn")
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("Create default user admin with password admin!");
            }
        };
    }
}
