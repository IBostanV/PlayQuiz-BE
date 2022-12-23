package com.play.quiz.service.impl;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.model.Category;
import com.play.quiz.repository.QuestionRepository;
import com.play.quiz.service.QuizQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private QuestionMapper questionMapper;
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionDto> findAll() {
        return questionMapper.mapToDtoList(questionRepository.findAll());
    }

    @Override
    public List<QuestionDto> getCategoryQuestions(final Category category) {
        return questionMapper.mapToDtoList(questionRepository.findByCategory(category));
    }
}
