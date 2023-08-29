package com.play.quiz.controller;

import com.play.quiz.domain.Message;
import com.play.quiz.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import static com.play.quiz.controller.RestEndpoint.REQUEST_MAPPING_MESSAGE;
import static com.play.quiz.controller.RestEndpoint.WS_BROKER_PARTY;

@Slf4j
@RestController
@RequestMapping(RestEndpoint.CONTEXT_PATH + REQUEST_MAPPING_MESSAGE)
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    private static final String SIMP_SESSION_ID = "simpSessionId";

    @MessageMapping("/public")
    @SendTo(WS_BROKER_PARTY + "/news")
    public Message sendPublicMessage(
            @Payload final Message payload, @Header(SIMP_SESSION_ID) final String sessionId, final Principal principal) {
        return messageService.sendPublicMessage(payload, sessionId, principal);
    }

    @MessageMapping("/private")
    public void sendPrivateMessage(
            @Payload final Message payload, @Header(SIMP_SESSION_ID) final String sessionId, final Principal principal) {
        messageService.sendPrivateMessage(payload, sessionId, principal);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> fetchMessageHistory(@RequestParam final String destination, final Principal principal) {
        return ResponseEntity.ok(messageService.fetchMessageHistory(destination, principal));
    }
}
