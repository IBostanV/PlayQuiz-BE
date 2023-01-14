package com.play.quiz.controller;

import com.play.quiz.model.message.MessagePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/private")
    public Map<String, Object> post(
            @Payload final String payload, final Principal principal, @Header("simpSessionId") final String sessionId) {
        return MessagePayload.from(principal.getName(), payload, sessionId, LocalDateTime.now());}
}
