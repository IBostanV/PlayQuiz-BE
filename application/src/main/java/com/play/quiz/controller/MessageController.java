package com.play.quiz.controller;

import com.play.quiz.model.message.MessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/public")
    @SendTo("/party/news")
    public MessagePayload sendPublicMessage(@Payload final String payload, final Principal principal) {
        return new MessagePayload(null, principal.getName(), payload, null, LocalDateTime.now());
    }

    @MessageMapping("/private")
    public void sendPrivateMessage(@Payload final MessagePayload payload, final Principal principal) {
        MessagePayload messagePayload = new MessagePayload(
                payload.getTo(), principal.getName(), payload.getContent(), null, LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(messagePayload.getTo(), "/solo", messagePayload);
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/solo", messagePayload);
    }
}
