package com.play.quiz.dto;

import com.play.quiz.enums.QuestionType;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
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

    String attributes;

    List<AnswerDto> answers;
}
