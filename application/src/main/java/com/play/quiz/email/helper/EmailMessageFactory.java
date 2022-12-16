package com.play.quiz.email.helper;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.model.VerificationToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailMessageFactory {
    @Value("${application.server.host.url}")
    private String serverHostUrl;
    @Value("${application.email.sending-address}")
    private String defaultSenderEmailAddress;

    private static final String activateAccountPath = "/auth/activate-account";
    private static final String accountActivationSubject = "Activate your account";
    private static final String accountActivationEmailTemplate = "confirmation-email.html";

    public EmailMessage createAccountVerificationEmailMessage(final AccountDto accountDto, final VerificationToken verificationToken) {
        final Map<String, Object> properties = Map.of("token", verificationToken.getToken(),
                "activateAccountServerHost", serverHostUrl + activateAccountPath);

        return EmailMessage.builder()
                .to(accountDto.getEmail())
                .from(defaultSenderEmailAddress)
                .subject(accountActivationSubject)
                .template(accountActivationEmailTemplate)
                .properties(properties)
                .build();
    }
}
