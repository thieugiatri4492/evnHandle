package tripqm.evn.java.apigateway.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tripqm.evn.java.apigateway.dto.ApiResponse;
import tripqm.evn.java.apigateway.dto.request.VerifyTokenRequest;
import tripqm.evn.java.apigateway.dto.response.VerifyTokenResponse;
import tripqm.evn.java.apigateway.repository.SystemClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SystemService {
    SystemClient verifyTokenClient;

    public Mono<ApiResponse<VerifyTokenResponse>> verifyToken(String token){
        return verifyTokenClient.verifyToken(VerifyTokenRequest.builder()
                                                                .token(token)
                                                                .build());
    }
}
