package tripqm.evn.java.system.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
    private String jwtSecret;
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        return "";
    }
}
