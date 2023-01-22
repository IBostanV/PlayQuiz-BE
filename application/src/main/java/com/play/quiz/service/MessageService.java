package com.play.quiz.service;

import com.play.quiz.model.Message;

import java.security.Principal;
import java.util.List;

public interface MessageService {

    void sendPrivateMessage(final Message payload, final String sessionId, final Principal principal);

    Message sendPublicMessage(final Message payload, final String sessionId, final Principal principal);

    List<Message> fetchMessageHistory(final String destination, final Principal principal);
}
