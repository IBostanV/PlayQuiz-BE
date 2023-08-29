package com.play.quiz.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.play.quiz.domain.Trophy;
import com.play.quiz.dto.wrapper.HistoryAnswer;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserHistoryDto {
    QuizDto quiz;
    Long historyId;
    Double spentTime;
    String answersJson;
    AccountDto account;
    Set<Trophy> trophies;
    LocalDateTime completedDate;
    List<HistoryAnswer> answers = new ArrayList<>();
}
