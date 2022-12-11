package com.play.quiz.email;

import com.play.quiz.email.helper.EmailMessage;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(final EmailMessage emailMessage) throws MessagingException;
}
