package tripqm.evn.java.system.security.jwt;

import java.security.Key;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import io.jsonwebtoken.*;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import tripqm.evn.java.system.service.impl.UserDetailsImpl;

import java.util.Date;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @NonFinal
    @Value("${tripqm.app.jwtSecret}")
    private String jwtSecret ;
    @NonFinal
    @Value("${tripqm.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
                .signWith(this.key(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateNimbusToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder().
                subject(username)
                .issuer("EVN")
                .issueTime(new Date())
                .expirationTime(new Date((new Date()).getTime() + this.jwtExpirationMs))
                .claim("userID","Custom")
                .build();
        Payload payloadToken = new Payload(jwtClaimSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header,payloadToken);
        //Symetric sign with MACSigner
        try {
            jwsObject.sign(new MACSigner(this.jwtSecret.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e){
           logger.error("Cannot create token",e);
            throw new RuntimeException(e);
        }

    }
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
    }


}
