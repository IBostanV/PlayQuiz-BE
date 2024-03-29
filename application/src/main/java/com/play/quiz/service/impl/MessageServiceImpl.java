package com.play.quiz.service.impl;

import static com.play.quiz.controller.RestEndpoint.WS_BROKER_SOLO;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import com.play.quiz.domain.Message;
import com.play.quiz.repository.MessageRepository;
import com.play.quiz.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public List<Message> fetchMessageHistory(String destination, final Principal principal) {
        return messageRepository.findBySourceAndDestination(principal.getName(), destination);
    }

    @Override
    public Message sendPublicMessage(final Message payload, String sessionId, final Principal principal) {
        Message message = builder(payload, sessionId, principal);
        return messageRepository.save(message);
    }

    @Override
    public void sendPrivateMessage(final Message payload, String sessionId, final Principal principal) {
        Message message = builder(payload, sessionId, principal);
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), WS_BROKER_SOLO, message);
        simpMessagingTemplate.convertAndSendToUser(message.getDestination(), WS_BROKER_SOLO, message);
    }

    private Message builder(final Message payload, String sessionId, final Principal principal) {
        return Message.builder()
                .sessionId(sessionId)
                .source(principal.getName())
                .content(payload.getContent())
                .createdDate(LocalDateTime.now())
                .destination(payload.getDestination())
                .build();
    }
}
