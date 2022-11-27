package com.play.quiz.dto;

import com.play.quiz.enums.QuestionCategory;
import com.play.quiz.enums.QuestionTopic;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class QuestionDto {
    Integer id;
    String content;
    int priority;
    int difficulty;
    QuestionTopic topic;
    QuestionCategory category;
}
