package com.play.quiz.email.helper;

import com.play.quiz.model.Account;
import com.play.quiz.model.VerificationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class EmailMessageFactory {
    @Value("${application.server.host.url}")
    private String serverHostUrl;
    @Value("${application.email.sending-address}")
    private String defaultSenderEmailAddress;

    private static final String activateAccountPath = "/auth/activate-account";
    private static final String accountActivationSubject = "Activate your account";
    private static final String accountActivationEmailTemplate = "confirmation-email.html";

    public EmailMessage createAccountVerificationEmailMessage(final Account account, final VerificationToken verificationToken) {
        Map<String, Object> properties = Map.of(
                "token", verificationToken.getToken(),
                "activateAccountServerHost", serverHostUrl + activateAccountPath);
        log.info("Creating EmailMessage with properties: " + properties);

        return EmailMessage.builder()
                .to(account.getEmail())
                .from(defaultSenderEmailAddress)
                .subject(accountActivationSubject)
                .template(accountActivationEmailTemplate)
                .properties(properties)
                .build();
    }
}
