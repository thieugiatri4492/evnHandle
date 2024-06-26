package tripqm.evn.java.system.security.jwt;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JOSEException;

import lombok.experimental.NonFinal;
import tripqm.evn.java.system.payload.request.IntrospectRequest;
import tripqm.evn.java.system.service.IntrospectService;

@Component
public class CustomJwtDecoder implements JwtDecoder {
    private final IntrospectService introspectService;
    public CustomJwtDecoder (IntrospectService introspectService){
        this.introspectService = introspectService;
    }

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @NonFinal
    @Value("${tripqm.app.jwtSecret}")
    private String jwtSecret;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            var response = introspectService.introspect(
                    IntrospectRequest.builder().token(token).build());

            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException e) {
            throw new JwtException(e.getMessage());
        }

        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), "HS512");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
