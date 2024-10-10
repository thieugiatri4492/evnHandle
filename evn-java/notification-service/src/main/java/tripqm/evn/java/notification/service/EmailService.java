package tripqm.evn.java.notification.service;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tripqm.evn.java.notification.dto.request.EmailRequest;
import tripqm.evn.java.notification.dto.request.SendEmailRequest;
import tripqm.evn.java.notification.dto.request.Sender;
import tripqm.evn.java.notification.dto.response.EmailResponse;
import tripqm.evn.java.notification.exception.ApiException;
import tripqm.evn.java.notification.exception.ErrorCode;
import tripqm.evn.java.notification.repository.httpclient.EmailClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey ;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("TriPQM")
                        .email("hokage4492@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new ApiException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
