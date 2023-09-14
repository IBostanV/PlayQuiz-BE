package com.play.quiz.service;

import com.play.quiz.dto.UserQuizHistoryDto;
import org.springframework.stereotype.Service;

@Service
public interface UserQuizHistoryService {

    UserQuizHistoryDto save(final UserQuizHistoryDto userQuizHistoryDto);

    UserQuizHistoryDto getById(final Long historyId);
}
