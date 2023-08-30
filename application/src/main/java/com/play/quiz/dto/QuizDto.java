package com.play.quiz.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.play.quiz.enums.QuizType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class QuizDto {
    Long quizId;
    Integer quizTime;
    QuizType quizType;
    Set<Long> questionIds;
    LocalDateTime updatedDate;
    LocalDateTime createdDate;
    @NotNull CategoryDto category;
    List<QuestionDto> questionList;
    @PositiveOrZero int questionsCount;
}
