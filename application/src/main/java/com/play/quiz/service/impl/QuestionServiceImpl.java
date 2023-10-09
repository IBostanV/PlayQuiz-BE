package com.play.quiz.service.impl;

import static com.play.quiz.util.Constant.EXPRESS_QUIZ_DEFAULT_OPTIONS_COUNT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.play.quiz.domain.Answer;
import com.play.quiz.domain.Category;
import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.GlossaryType;
import com.play.quiz.domain.Property;
import com.play.quiz.domain.Question;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.engine.QuestionGenerationEngine;
import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.repository.PropertyRepository;
import com.play.quiz.repository.QuestionRepository;
import com.play.quiz.service.AnswerService;
import com.play.quiz.service.GlossaryService;
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

    private final AnswerService answerService;
    private final QuestionMapper questionMapper;
    private final GlossaryService glossaryService;
    private final PropertyRepository propertyRepository;
    private final QuestionRepository questionRepository;
    private final QuestionGenerationEngine generationEngine;

    @Override
    @Transactional
    public QuestionDto save(final QuestionDto questionDto) {
        Question question = questionMapper.mapToEntity(questionDto);
        Question savedQuestion = questionRepository.save(processQuestion(question));

        return questionMapper.mapToDto(savedQuestion);
    }

    private Question processQuestion(final Question question) {
        question.fillAnswersParent();
        question.fillTranslationsParent();
        handleAnswersContent(question);

        return question;
    }

    private void handleAnswersContent(final Question question) {
        question.getAnswers().forEach(answer -> Optional.ofNullable(answer.getGlossary())
                .ifPresent(glossary -> answer.setContent(processAnswerByGlossary(question.getAttributes(), glossary))));
    }

    private String processAnswerByGlossary(final List<QuestionAttribute> attributes, final Glossary answerGlossary) {
        Glossary glossary = glossaryService.getEntityById(answerGlossary.getTermId());
        return attributes.contains(QuestionAttribute.ANSWER_BY_KEY) ? glossary.getKey() : glossary.getValue();
    }

    @Override
    @Transactional(readOnly = true)
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
    public Question getById(final Long questionId) {
        return questionRepository.getReferenceById(questionId);
    }

    @Override
    public List<Question> getByIds(final Set<Long> idList) {
        return questionRepository.findAllByQuestionIdIn(idList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnswerDto> getAnswers(final Long questionId) {
        Question question = questionRepository.getReferenceById(questionId);
        return questionMapper.mapToDto(question).getAnswers();
    }

    @Override
    public List<Question> getByCategoryId(Long catId, int questionCount) {
        Pageable pageable = PageRequest.of(0, questionCount);
        return questionRepository.getByCategory_catId(catId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionDto getQuestionWithOptions(final Long questionId) {
        Question question = questionRepository.getReferenceById(questionId);
        handleAnswerDiscrepancy(question);
        return questionMapper.mapToDto(question);
    }

    private void handleAnswerDiscrepancy(final Question question) {
        Property optionsCountProperty = propertyRepository.findByName(EXPRESS_QUIZ_DEFAULT_OPTIONS_COUNT);
        int optionsCount = optionsCountProperty.getIntValue();
        List<Answer> answers = question.getAnswers();

        if (answers.size() < optionsCount) {
            List<Answer> wrongAnswerOptions = fillAnswerOptions(answers, question.getCategory(), optionsCount);
            answers.addAll(wrongAnswerOptions);
        }
    }

    private List<Answer> fillAnswerOptions(final List<Answer> answers, final Category category, int optionsCount) {
        int optionsAmount = optionsCount - answers.size();
        GlossaryType answerGlossaryType = getGlossaryTypeFor(answers);
        List<Long> answerIdList = answers.stream().map(Answer::getAnsId).toList();

        if (Objects.nonNull(answerGlossaryType)) {
            return answerService.getWrongOptionsByGlossaryTypeWithLimit(answerGlossaryType, answerIdList, optionsAmount);
        }
        return answerService.getWrongOptionsByCategoryIdWithLimit(category.getCatId(), answerIdList, optionsAmount);
    }

    private static GlossaryType getGlossaryTypeFor(final List<Answer> answers) {
        return answers.stream()
                .findFirst()
                .map(Answer::getGlossary)
                .map(Glossary::getType)
                .orElse(null);
    }
}
