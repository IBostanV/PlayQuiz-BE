package com.ibos.dto;

import com.ibos.enums.QuestionCategory;
import com.ibos.enums.QuestionTopic;
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
