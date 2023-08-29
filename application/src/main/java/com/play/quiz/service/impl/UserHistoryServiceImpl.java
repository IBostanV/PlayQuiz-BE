package com.play.quiz.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.play.quiz.domain.Answer;
import com.play.quiz.domain.Question;
import com.play.quiz.domain.Quiz;
import com.play.quiz.domain.UserHistory;
import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.dto.UserHistoryDto;
import com.play.quiz.dto.wrapper.HistoryAnswer;
import com.play.quiz.exception.RecordNotFoundException;
import com.play.quiz.mapper.UserHistoryMapper;
import com.play.quiz.repository.UserHistoryRepository;
import com.play.quiz.security.AuthenticationFacade;
import com.play.quiz.service.GlossaryService;
import com.play.quiz.service.QuestionService;
import com.play.quiz.service.UserHistoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserHistoryServiceImpl implements UserHistoryService {

    private final QuestionService questionService;
    private final GlossaryService glossaryService;
    private final UserHistoryMapper userHistoryMapper;
    private final AuthenticationFacade authenticationFacade;
    private final UserHistoryRepository userHistoryRepository;

    @Override
    public UserHistoryDto save(final UserHistoryDto userHistoryDto) {
        UserHistory userHistory = userHistoryMapper.toEntity(userHistoryDto).toBuilder()
                .completedDate(LocalDateTime.now())
                .account(authenticationFacade.getAccount())
                .build();
        UserHistory savedEntity = userHistoryRepository.save(userHistory);

        return userHistoryMapper.toDto(savedEntity);
    }

    @NonNull
    @Override
    @Transactional
    public UserHistoryDto getById(final Long historyId) {
        log.debug("Get UserHistory with historyId: " + historyId);
        UserHistory userHistory = userHistoryRepository.getReferenceById(historyId);
        List<Question> questionList = questionService.getByIds(userHistory.getQuiz().getQuestionIds());

        Quiz quiz = userHistory.getQuiz().toBuilder().questionList(questionList).build();
        UserHistory userHistoryWithQuestions = userHistory.toBuilder().quiz(quiz).build();
        UserHistoryDto historyDto = userHistoryMapper.toDto(userHistoryWithQuestions);
        handleJsonAnswers(historyDto, questionList);

        return historyDto;
    }

    private void handleJsonAnswers(final UserHistoryDto historyDto, final List<Question> questionList) {
        JsonArray jsonElements = JsonParser.parseString(historyDto.getAnswersJson()).getAsJsonArray();

        jsonElements.forEach(jsonElement -> {
            Set<Map.Entry<String, JsonElement>> answersEntrySet = jsonElement.getAsJsonObject().entrySet();
            answersEntrySet.forEach(jsonKeyValue -> questionList.stream()
                    .filter(question -> Objects.equals(question.getQuestionId(), Long.parseLong(jsonKeyValue.getKey())))
                    .forEach(question -> historyDto.getAnswers().add(fillHistoryAnswers(jsonKeyValue, question))));
        });
    }

    private HistoryAnswer fillHistoryAnswers(final Map.Entry<String, JsonElement> jsonKeyValue, final Question question) {
        Answer answer = question.getAnswers().stream().findFirst().orElseThrow(
                () -> new RecordNotFoundException("No answers found for question: " + question.getQuestionId()));

        long glossaryId = jsonKeyValue.getValue().getAsLong();
        if (Objects.equals(answer.getGlossary().getTermId(), glossaryId)) {
            log.debug("User answered right. Glossary id: " + glossaryId);
            return buildHistoryAnswer(question.getContent(), answer.getContent(), null);
        } else {
            log.debug("User answered wrong. Get wrong answer by glossary id: " + glossaryId);
            GlossaryDto glossaryDto = glossaryService.getById(glossaryId);
            return buildHistoryAnswer(question.getContent(), glossaryDto.getValue(), answer.getContent());
        }
    }

    private HistoryAnswer buildHistoryAnswer(final String content, final String userAnswer, final String rightAnswer) {
        return HistoryAnswer.builder()
                .content(content)
                .userAnswer(userAnswer)
                .rightAnswer(rightAnswer)
                .build();
    }
}
