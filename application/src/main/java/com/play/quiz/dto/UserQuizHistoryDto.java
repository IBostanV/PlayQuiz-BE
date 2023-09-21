package com.play.quiz.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.play.quiz.domain.Trophy;
import com.play.quiz.dto.wrapper.HistoryAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQuizHistoryDto {
    private QuizDto quiz;
    private Long historyId;
    private Double spentTime;
    private String answersJson;
    private AccountDto account;
    private Set<Trophy> trophies;
    private LocalDateTime completedDate;

    @Builder.Default
    private List<HistoryAnswer> answers = new ArrayList<>();
}
