package com.play.quiz.email;

import com.play.quiz.email.helper.EmailMessage;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Value("${application.email.sending.enabled:true}")
    private boolean emailEnabled;
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(final EmailMessage emailMessage) throws MessagingException {
        MimeMessage mimeMessage = configureEmailMessage(emailMessage);
        if (emailEnabled) emailSender.send(mimeMessage);
    }

    private MimeMessage configureEmailMessage(final EmailMessage emailMessage) throws MessagingException {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
        configureEmailMessage(emailMessage, mimeMessageHelper);

        return mimeMessage;
    }

    private void configureEmailMessage(final EmailMessage emailMessage, final MimeMessageHelper mimeMessageHelper) throws MessagingException {
        log.debug("Configure MailMessage using email message: " + emailMessage);
        Context context = new Context(Locale.getDefault(), emailMessage.getProperties());
        String emailText = templateEngine.process(emailMessage.getTemplate(), context);

        mimeMessageHelper.setTo(emailMessage.getTo());
        mimeMessageHelper.setText(emailText, true);
        mimeMessageHelper.setFrom(emailMessage.getFrom());
        mimeMessageHelper.setSubject(emailMessage.getSubject());
    }
}
