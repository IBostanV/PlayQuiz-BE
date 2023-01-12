package com.play.quiz.service.impl;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.engine.QuestionGenerationEngine;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.model.Category;
import com.play.quiz.model.Question;
import com.play.quiz.repository.QuestionRepository;
import com.play.quiz.service.QuestionService;
import com.play.quiz.util.SystemAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final QuestionGenerationEngine generationEngine;

    @Override
    public QuestionDto save(final QuestionDto questionDto) {
        Question question = questionMapper.mapToEntity(questionDto);
        Question savedQuestion = questionRepository.save(question);
        return questionMapper.mapToDto(savedQuestion);
    }

    @Override
    public List<QuestionDto> findAll() {
        List<Question> questions = questionRepository.findAll();
        return questionMapper.mapToDtoList(questions);
    }

    @Override
    public List<QuestionDto> findByCategory(final Category category) {
        List<Question> questions = questionRepository.findByCategory_naturalId(category.getNaturalId());
        return questionMapper.mapToDtoList(questions);
    }

    @Override
    public List<QuestionDto> generateFromTemplate(final QuestionDto questionDto) {
        SystemAssert.isTemplateQuestion(questionDto);
        Question question = questionMapper.mapToEntity(questionDto);
        return questionMapper.mapToDtoList(generationEngine.generateFromCreatedTemplate(question));
    }

    @Override
    public void deactivate(final Long questionId) {
        questionRepository.deactivate(questionId);
    }
}
