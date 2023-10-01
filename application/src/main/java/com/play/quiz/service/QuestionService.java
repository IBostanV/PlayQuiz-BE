package com.play.quiz.service;

import java.util.List;
import java.util.Set;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.domain.Category;
import com.play.quiz.domain.Question;

public interface QuestionService {

    QuestionDto save(final QuestionDto questionDto);

    List<QuestionDto> findAll();

    List<QuestionDto> findByCategory(final Category category);

    List<QuestionDto> generateFromTemplate(final QuestionDto questionDto);

    void deactivate(final Long questionId);

    List<Question> getGeneralKnowledgeQuestions(int questionCount);

    Question getById(final Long questionId);

    List<Question> getByIds(final Set<Long> idList);

    List<AnswerDto> getAnswers(final Long questionId);

    QuestionDto getQuestionWithOptions(final Long questionId);

    List<Question> getByCategoryId(Long catId, int questionCount);
}
