package com.play.quiz.email;

import com.play.quiz.email.helper.EmailMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendEmail(final EmailMessage emailMessage) throws MessagingException {
        final MimeMessage mimeMessage = configureMailMessage(emailMessage);
        emailSender.send(mimeMessage);
    }

    private MimeMessage configureMailMessage(final EmailMessage emailMessage) throws MessagingException {
        final MimeMessage mimeMessage = emailSender.createMimeMessage();
        final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
        configureMailMessage(emailMessage, mimeMessageHelper);

        return mimeMessage;
    }

    private void configureMailMessage(final EmailMessage emailMessage, final MimeMessageHelper mimeMessageHelper) throws MessagingException {
        final Context context = new Context();
        context.setVariables(emailMessage.getProperties());
        final String emailText = templateEngine.process(emailMessage.getTemplate(), context);

        mimeMessageHelper.setTo(emailMessage.getTo());
        mimeMessageHelper.setText(emailText, true);
        mimeMessageHelper.setFrom(emailMessage.getFrom());
        mimeMessageHelper.setSubject(emailMessage.getSubject());
    }
}
