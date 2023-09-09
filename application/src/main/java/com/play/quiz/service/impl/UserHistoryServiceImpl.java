package com.play.quiz.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.play.quiz.domain.Question;
import com.play.quiz.domain.Quiz;
import com.play.quiz.domain.UserHistory;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.dto.QuestionDto;
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
        UserHistory userHistory = userHistoryMapper.toEntity(userHistoryDto);
        return userHistoryMapper.toDto(saveUserHistory(userHistory));
    }

    private UserHistory saveUserHistory(final UserHistory userHistory) {
        return userHistoryRepository.save(userHistory.toBuilder()
                .completedDate(LocalDateTime.now())
                .account(authenticationFacade.getAccount())
                .build());
    }

    @NonNull
    @Override
    @Transactional
    public UserHistoryDto getById(final Long historyId) {
        log.debug("Get UserHistory with historyId: " + historyId);
        UserHistoryDto historyDto = buildUserHistory(historyId);
        JsonArray jsonUserAnswers = JsonParser.parseString(historyDto.getAnswersJson()).getAsJsonArray();

        historyDto.getQuiz().getQuestionList().forEach(question ->
                historyDto.getAnswers().addAll(createUserAnswersFromJson(jsonUserAnswers, question)));

        return historyDto;
    }

    private UserHistoryDto buildUserHistory(final Long historyId) {
        UserHistory userHistory = userHistoryRepository.getReferenceById(historyId);
        List<Question> questionList = questionService.getByIds(userHistory.getQuiz().getQuestionIds());
        Quiz quiz = userHistory.getQuiz().toBuilder().questionList(questionList).build();

        return userHistoryMapper.toDto(userHistory.toBuilder().quiz(quiz).build());
    }

    private List<HistoryAnswer> createUserAnswersFromJson(final JsonArray userAnswers, final QuestionDto question) {
        if (!userAnswers.isEmpty()) {
            List<HistoryAnswer> singleAnswerAsList = getAnswers(userAnswers, question);
            if (!singleAnswerAsList.isEmpty()) return singleAnswerAsList;
        }

        return Collections.singletonList(new HistoryAnswer(
                0, question.getContent(), null, getFirstAnswer(question).getContent()));
    }

    private List<HistoryAnswer> getAnswers(JsonArray userAnswers, QuestionDto question) {
        for (JsonElement userAnswer : userAnswers) {
            final Set<String> questionIds = userAnswer.getAsJsonObject().keySet();
            final Function<Map.Entry<String, JsonElement>, HistoryAnswer> createAnswer =
                    keyValue -> createHistoryAnswer(keyValue.getValue().getAsJsonObject(), question);

            if (questionIds.contains(question.getId().toString())) {
                log.info("User answered the question: " + question.getId());
                return userAnswer.getAsJsonObject().entrySet()
                        .stream().map(createAnswer).collect(Collectors.toList());
            }
        }

        log.info("User did not answer the question.");
        return Collections.emptyList();
    }

    private HistoryAnswer createHistoryAnswer(final JsonObject keyValue, final QuestionDto question) {
        AnswerDto answer = getFirstAnswer(question);
        Long glossaryId = getJsonElementValue(keyValue, "answer").getAsLong();
        double time = getJsonElementValue(keyValue, "time").getAsDouble();

        if (Objects.equals(answer.getGlossary().getTermId(), glossaryId)) {
            log.debug("User answered right. Glossary id: " + glossaryId);
            return new HistoryAnswer(time, question.getContent(), answer.getContent(), null);
        }

        log.debug("User answered wrong. Get user answer by glossary id: " + glossaryId);
        GlossaryDto glossaryDto = glossaryService.getById(glossaryId);
        return new HistoryAnswer(time, question.getContent(), glossaryDto.getValue(), answer.getContent());
    }

    private static JsonElement getJsonElementValue(final JsonObject keyValue, final String key) {
        return Objects.requireNonNull(keyValue.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getKey(), key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null));
    }

    private static AnswerDto getFirstAnswer(final QuestionDto question) {
        return question.getAnswers().stream().findFirst()
                .orElseThrow(() -> new RecordNotFoundException("No answers found for question: " + question.getId()));
    }
}
