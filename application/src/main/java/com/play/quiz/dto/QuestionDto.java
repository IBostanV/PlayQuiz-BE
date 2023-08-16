package com.play.quiz.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.play.quiz.dto.translation.QuestionTranslationDto;
import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode(exclude = {"answers", "translations"})
public class QuestionDto {
    Long id;
    Object tipId;
    String topic;
    int priority;
    Boolean isActive;
    QuestionType type;
    int complexityLevel;
    List<AnswerDto> answers;
    @NotBlank String content;
    AccountDto createdAccount;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
    AccountDto updatedAccount;
    @NotNull CategoryDto category;
    @Builder.Default
    List<QuestionAttribute> attributes = new ArrayList<>();
    @Builder.Default
    List<QuestionTranslationDto> translations = new ArrayList<>();
}
