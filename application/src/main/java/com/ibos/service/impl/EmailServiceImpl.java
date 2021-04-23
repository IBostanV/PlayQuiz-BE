package com.ibos.service.impl;

import com.ibos.dto.EmailDto;
import com.ibos.service.EmailService;
import com.ibos.test.QuartzJobExecutor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String TEMPLATE = "welcome";

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final QuartzJobExecutor quartzJobExecutor;

    public EmailServiceImpl(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine, QuartzJobExecutor quartzJobExecutor) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.quartzJobExecutor = quartzJobExecutor;
    }

    @Async
    @SneakyThrows
    public void sendEmail(EmailDto emailDto) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.setFrom(emailDto.getFrom());
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(templateEngine.process(TEMPLATE, new Context()), true);

        javaMailSender.send(message);
        quartzJobExecutor.executeJob();
    }
}
