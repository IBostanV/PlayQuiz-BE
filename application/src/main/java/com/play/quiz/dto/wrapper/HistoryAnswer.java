package com.play.quiz.dto.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryAnswer {
    private String content;
    private String userAnswer;
    private String rightAnswer;
}
