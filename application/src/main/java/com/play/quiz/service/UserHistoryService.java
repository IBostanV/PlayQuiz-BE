package com.play.quiz.service;

import com.play.quiz.dto.UserHistoryDto;
import org.springframework.stereotype.Service;

@Service
public interface UserHistoryService {

    UserHistoryDto save(final UserHistoryDto userHistoryDto);

    UserHistoryDto getById(final Long historyId);
}
