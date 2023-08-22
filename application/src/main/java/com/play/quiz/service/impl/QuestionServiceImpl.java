package com.play.quiz.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.engine.QuestionGenerationEngine;
import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.model.Category;
import com.play.quiz.model.Glossary;
import com.play.quiz.model.Question;
import com.play.quiz.repository.GlossaryRepository;
import com.play.quiz.repository.QuestionRepository;
import com.play.quiz.service.QuestionService;
import com.play.quiz.util.SystemAssert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;
    private final QuestionRepository questionRepository;
    private final GlossaryRepository glossaryRepository;
    private final QuestionGenerationEngine generationEngine;

    @Override
    @Transactional
    public QuestionDto save(final QuestionDto questionDto) {
        Question question = questionMapper.mapToEntity(questionDto);
        Question savedQuestion = questionRepository.save(
                processQuestion(question));
        return questionMapper.mapToDto(savedQuestion);
    }

    private Question processQuestion(final Question question) {
        question.handleAnswersParent();
        question.handleTranslationsParent();
        return handleAnswersContent(question);
    }

    private Question handleAnswersContent(final Question question) {
        question.getAnswers().forEach(answer -> Optional.ofNullable(answer.getGlossary())
                .ifPresent(glossary -> answer.setContent(processAnswerByGlossary(question.getAttributes(), glossary))));
        return question;
    }

    private String processAnswerByGlossary(final List<QuestionAttribute> attributes, final Glossary answerGlossary) {
        Glossary glossary = glossaryRepository.getReferenceById(answerGlossary.getTermId());
        return attributes.contains(QuestionAttribute.ANSWER_BY_KEY) ? glossary.getKey() : glossary.getValue();
    }

    @Override
    @Transactional
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

    @Override
    public List<Question> getGeneralKnowledgeQuestions(int questionCount) {
        Pageable pageable = PageRequest.of(0, questionCount);
        Page<Question> entities = questionRepository.findAll(pageable);
        return entities.getContent();
    }

    @Override
    public List<Question> getByIds(final Set<Long> idList) {
        return questionRepository.findAllByQuestionIdIn(idList);
    }

    @Override
    @Transactional
    public List<AnswerDto> getAnswers(final Long questionId) {
        Question question = questionRepository.getReferenceById(questionId);
        return questionMapper.mapToDto(question).getAnswers();
    }
}
