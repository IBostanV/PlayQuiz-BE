package com.play.quiz.service.impl;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.enums.QuestionCategory;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.repository.QuizQuestionRepository;
import com.play.quiz.service.QuizQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private QuestionMapper questionMapper;
    private QuizQuestionRepository quizQuestionRepository;

    @Override
    public List<QuestionDto> findAll() {
        return questionMapper.mapToDtoList(quizQuestionRepository.findAll());
    }

    @Override
    public List<QuestionDto> getCategoryQuestions(QuestionCategory category) {
        return questionMapper.mapToDtoList(quizQuestionRepository.findByCategory(category));
    }
}
