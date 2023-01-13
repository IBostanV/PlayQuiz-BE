package com.play.quiz.service.impl;

import com.play.quiz.dto.QuizDto;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.mapper.QuizMapper;
import com.play.quiz.model.Category;
import com.play.quiz.model.Question;
import com.play.quiz.model.Quiz;
import com.play.quiz.repository.QuizRepository;
import com.play.quiz.service.QuestionService;
import com.play.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;

    @Override
    public QuizDto create(final QuizDto quizDto) {
        List<Question> questions = questionMapper.mapToEntityList(
                questionService.findByCategory(getCategory(quizDto)));
        handleQuestionDiscrepancy(quizDto, questions);
        return quizMapper.toDto(
                quizRepository.save(
                        buildQuiz(questions, quizDto)));
    }

    private static void handleQuestionDiscrepancy(final QuizDto quizDto, final List<Question> questions) {
        while (questions.size() < quizDto.getQuestionsCount()) {
            Collections.shuffle(questions);
            questions.add(questions.get(0));
        }
    }

    private Quiz buildQuiz(final List<Question> questionList, final QuizDto quizDto) {
        Collections.shuffle(questionList);
        List<Question> quizQuestions = questionList.subList(0, quizDto.getQuestionsCount());

        return Quiz.builder()
                .questionList(questionList)
                .category(getCategory(quizDto))
                .questionsCount(quizQuestions.size())
                .createdDate(quizDto.getCreatedDate())
                .questionIds(getQuestionIds(quizQuestions))
                .build();
    }

    private static Category getCategory(QuizDto quizDto) {
        return Category.builder()
                .catId(quizDto.getCategory().getCatId())
                .name(quizDto.getCategory().getName())
                .naturalId(quizDto.getCategory().getNaturalId())
                .build();
    }

    private static Set<Long> getQuestionIds(final List<Question> quizQuestions) {
        return quizQuestions.stream()
                .map(Question::getQuestionId)
                .collect(Collectors.toSet());
    }

    @Override
    public QuizDto getById(final Long quizId) {
        return quizMapper.INSTANCE.toDto(
                quizRepository.getReferenceById(quizId));
    }
}
