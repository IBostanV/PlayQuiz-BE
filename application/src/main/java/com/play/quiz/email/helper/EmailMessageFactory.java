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

    private static final String ACTIVATE_ACCOUNT_PATH = "/auth/activate-account";
    private static final String ACCOUNT_ACTIVATION_SUBJECT = "Activate your account";
    private static final String ACCOUNT_ACTIVATION_EMAIL_TEMPLATE = "confirmation-email.html";

    public EmailMessage createAccountVerificationEmailMessage(final Account account, final VerificationToken verificationToken) {
        Map<String, Object> properties = Map.of(
                "token", verificationToken.getToken(),
                "activateAccountServerHost", serverHostUrl + ACTIVATE_ACCOUNT_PATH);
        log.info("Creating EmailMessage with properties: " + properties);

        return EmailMessage.builder()
                .to(account.getEmail())
                .from(defaultSenderEmailAddress)
                .subject(ACCOUNT_ACTIVATION_SUBJECT)
                .template(ACCOUNT_ACTIVATION_EMAIL_TEMPLATE)
                .properties(properties)
                .build();
    }
}
