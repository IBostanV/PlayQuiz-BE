package com.play.quiz.dto;

import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@EqualsAndHashCode(exclude = {"answers"})
public class QuestionDto {

    Long id;

    AccountDto createdAccount;

    QuestionType type;

    Object tipId;

    @NotNull CategoryDto category;

    boolean isActive;

    int complexityLevel;

    @NotBlank String content;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;

    AccountDto updatedAccount;

    String topic;

    int priority;

    List<QuestionAttribute> attributes;

    List<AnswerDto> answers;
}
