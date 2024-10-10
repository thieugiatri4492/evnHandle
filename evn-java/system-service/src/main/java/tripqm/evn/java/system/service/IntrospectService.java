package tripqm.evn.java.system.service;

import java.text.ParseException;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import tripqm.evn.java.system.exception.ApiException;
import tripqm.evn.java.system.payload.request.IntrospectRequest;
import tripqm.evn.java.system.payload.response.IntrospectResponse;
import tripqm.evn.java.system.security.jwt.JwtUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IntrospectService {
    JwtUtils jwtUtils;

    public IntrospectResponse introspect(IntrospectRequest request)  {
        var token = request.getToken();
        boolean isValid = true;

        try {
            this.jwtUtils.verifyNimbusToken(token, false);
        } catch (ApiException  | JOSEException | ParseException e) {
            isValid = false;
        }

        return IntrospectResponse.builder().valid(isValid).build();
    }
}
