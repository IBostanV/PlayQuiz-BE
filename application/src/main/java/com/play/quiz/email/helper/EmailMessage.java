package com.play.quiz.email.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
public class EmailMessage {
    private String to;
    private String from;
    private String subject;
    private String template;
    private Map<String, Object> properties;
}
