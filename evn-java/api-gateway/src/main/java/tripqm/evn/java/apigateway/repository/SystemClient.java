package tripqm.evn.java.apigateway.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import tripqm.evn.java.apigateway.dto.ApiResponse;
import tripqm.evn.java.apigateway.dto.request.VerifyTokenRequest;
import tripqm.evn.java.apigateway.dto.response.VerifyTokenResponse;

public interface SystemClient {
    @PostExchange(url = "/auth/verifyToken", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<VerifyTokenResponse>> verifyToken(@RequestBody VerifyTokenRequest request);
}
