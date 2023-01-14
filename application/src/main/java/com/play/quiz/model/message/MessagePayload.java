package com.play.quiz.model.message;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessagePayload {

    public static Map<String, Object> from(final String user, final String content, final String sessionId, final LocalDateTime createDate) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final Map<String, Object> values = new HashMap<>();
        values.put("user", user);
        values.put("content", content);
        values.put("sessionId", sessionId);
        values.put("createDate", formatter.format(createDate));

        return values;
    }
}
