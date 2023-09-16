package com.play.quiz.service;

import com.play.quiz.domain.Message;

import java.security.Principal;
import java.util.List;

public interface MessageService {

    void sendPrivateMessage(final Message payload, String sessionId, final Principal principal);

    Message sendPublicMessage(final Message payload, String sessionId, final Principal principal);

    List<Message> fetchMessageHistory(String destination, final Principal principal);
}
