package com.play.quiz.service;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.model.Category;

import java.util.List;

public interface QuestionService {

    QuestionDto save(final QuestionDto questionDto);

    List<QuestionDto> findAll();

    List<QuestionDto> findByCategory(final Category category);

    List<QuestionDto> generateFromTemplate(final QuestionDto questionDto);

    void deactivate(final Long questionId);
}
