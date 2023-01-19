package com.play.quiz.model.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagePayload {
    private String to;
    private String from;
    private String content;
    private String sessionId;
    private LocalDateTime createDate;
}
