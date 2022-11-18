package com.ibos.service.impl;

import com.ibos.dto.QuestionDto;
import com.ibos.enums.QuestionCategory;
import com.ibos.mapper.QuestionMapper;
import com.ibos.repository.QuizQuestionRepository;
import com.ibos.service.QuizQuestionService;
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
