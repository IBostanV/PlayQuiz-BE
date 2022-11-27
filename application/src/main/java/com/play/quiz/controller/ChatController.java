package com.play.quiz.controller;

import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.runAsync;

@RestController
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ChatController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/get-user-list")
    public void getUserList() {
        System.out.println("We are here");
    }

    @CrossOrigin
    @GetMapping("/sse")
    public SseEmitter getSSeEvents() {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(sseEmitter::complete);

        Stream<String> stream = Stream.of("10", "20", "30", "40", "50");

        runAsync(() -> {
            stream.forEach(string -> {
                sleep(sseEmitter);
                pushProgress(sseEmitter, string);
            });
        });

        return sseEmitter;
    }

    private void pushProgress(SseEmitter sseEmitter, String data) {
        try {
            sseEmitter.send(
                SseEmitter
                    .event()
                    .name("StringList")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void sleep(SseEmitter sseEmitter) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
            sseEmitter.completeWithError(exception);
        }
    }

    @MessageMapping("/public")
    @SendToUser("/topic/private")
    public Map<String, String> post(@Payload Map<String, String> message, Principal principal, @Header("simpSessionId") String sessionId) throws InterruptedException {
//        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//        headerAccessor.setSessionId(sessionId);
//        headerAccessor.setLeaveMutable(true);
//
//        if (message.get("action").equals(MessageActions.PRINT.name())) {
//            return Map.of("message", "Message PRINT received from server ", "action", MessageActions.PRINT.name());
//        }
//
//        for (int i = 0; i < 3; i++) {
//            simpMessagingTemplate.convertAndSendToUser(
//                    sessionId,
//                    "/topic/private",
//                    Map.of("message", principal.getName() + i, "action", MessageActions.PROCESS.name()),
//                    headerAccessor.getMessageHeaders());
//            Thread.sleep(5000);
//        }

        return Map.of("message", "Request successfully done", "action", "Action");
    }
}
