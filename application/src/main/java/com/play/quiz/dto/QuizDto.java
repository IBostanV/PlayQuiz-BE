package com.play.quiz.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.play.quiz.enums.QuizType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private Long quizId;
    private Integer quizTime;
    private QuizType quizType;
    private Set<Long> questionIds;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
    private List<QuestionDto> questionList;

    @PositiveOrZero
    private int questionsCount;
    @NotNull
    private CategoryDto category;
}
