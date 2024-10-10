package tripqm.evn.java.system.security.jwt;

import java.security.Key;
import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.system.domain.S_InvalidToken;
import tripqm.evn.java.system.domain.S_User;
import tripqm.evn.java.system.enums.ErrorCode;
import tripqm.evn.java.system.exception.ApiException;
import tripqm.evn.java.system.payload.request.RefreshTokenRequest;
import tripqm.evn.java.system.payload.response.JwtResponse;
import tripqm.evn.java.system.repository.SInvalidatedTokenRepository;
import tripqm.evn.java.system.repository.SUserRepository;
import tripqm.evn.java.system.service.impl.UserDetailsImpl;

@Component
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtUtils {
    private final SUserRepository sUserRepository;

    @NonFinal
    @Value("${tripqm.app.jwtSecret}")
    String jwtSecret;

    @NonFinal
    @Value("${tripqm.app.jwtExpirationMs}")
    int jwtExpirationMs;

    @NonFinal
    @Value("${tripqm.app.jwtRefreshableMs}")
    int jwtRefreshableMs;

    SInvalidatedTokenRepository sInvalidatedTokenRepository;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + this.jwtExpirationMs))
                .signWith(this.key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateNimbusToken(S_User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toString())
                .issuer("EVN")
                .issueTime(new Date())
                .jwtID(UUID.randomUUID().toString())
                .expirationTime(new Date(Instant.now()
                        .plus(this.jwtExpirationMs, ChronoUnit.MILLIS)
                        .toEpochMilli()))
                .claim("scope", buildScope(user))
                .build();
        Payload payloadToken = new Payload(jwtClaimSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payloadToken);
        // Symetric sign with MACSigner
        try {
            jwsObject.sign(new MACSigner(this.jwtSecret.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    public SignedJWT verifyNimbusToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(this.jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT
                        .getJWTClaimsSet()
                        .getIssueTime()
                        .toInstant()
                        .plus(this.jwtRefreshableMs, ChronoUnit.MILLIS)
                        .toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new ApiException(ErrorCode.UNAUTHENTICATED);

        if (sInvalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new ApiException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public JwtResponse refreshNimbusToken(RefreshTokenRequest tokenRequest) throws ParseException, JOSEException {
        SignedJWT signedJWT = this.verifyNimbusToken(tokenRequest.getToken(), true);
        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        S_InvalidToken invalidatedToken =
                S_InvalidToken.builder().id(jit).expiryTime(expiryTime).build();
        sInvalidatedTokenRepository.save(invalidatedToken);
        var userName = signedJWT.getJWTClaimsSet().getSubject();
        var user = sUserRepository
                .findUserByUserName(userName)
                .orElseThrow(() -> new ApiException(ErrorCode.UNAUTHENTICATED));
        var token = this.generateNimbusToken(user);
        return JwtResponse.builder()
                .token(token)
                .id(user.getId())
                .userName(userName)
                .build();
    }

    private String buildScope(S_User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());

                if (!CollectionUtils.isEmpty(role.getPermissions()))
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });
        return stringJoiner.toString();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.jwtSecret));
    }
}
