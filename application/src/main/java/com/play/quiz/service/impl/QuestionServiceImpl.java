package com.play.quiz.service.impl;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.enums.CategoryType;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.model.Question;
import com.play.quiz.repository.QuestionRepository;
import com.play.quiz.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private QuestionMapper questionMapper;
    private QuestionRepository questionRepository;

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
    public List<QuestionDto> findByCategoryNaturalId(final CategoryType categoryType) {
        List<Question> questions = questionRepository.findByCategory_naturalId(categoryType.name());

        return questionMapper.mapToDtoList(questions);
    }

    @Override
    public void deleteById(Long questionId) {
        questionRepository.deleteById(questionId);
    }
}
