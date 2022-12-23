package com.play.quiz.dto;

import com.play.quiz.model.Account;
import com.play.quiz.model.Answer;
import com.play.quiz.model.Category;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class QuestionDto {

    Long questionId;

    Account account;

    String type;

    Object tipId;

    Category category;

    boolean isActive;

    int complexityLevel;

    String content;

    LocalDateTime createdDate;

    LocalDateTime updatedDate;

    Account updatedAccount;

    String topic;

    int priority;

    String attributes;

    List<Answer> answers;
}
