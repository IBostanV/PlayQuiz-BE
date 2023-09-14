package com.play.quiz.service.impl;

import static com.play.quiz.util.Constant.EXPRESS_QUIZ_DEFAULT_ANSWER_COUNT;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.play.quiz.domain.Property;
import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.CategoryDto;
import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.engine.QuestionGenerationEngine;
import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.domain.Category;
import com.play.quiz.domain.Glossary;
import com.play.quiz.domain.Question;
import com.play.quiz.repository.PropertyRepository;
import com.play.quiz.repository.QuestionRepository;
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

    private final QuestionMapper questionMapper;
    private final GlossaryService glossaryService;
    private final PropertyRepository propertyRepository;
    private final QuestionRepository questionRepository;
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
        Glossary glossary = glossaryService.getEntityById(answerGlossary.getTermId());
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
    public Question getById(final Long questionId) {
        return questionRepository.getReferenceById(questionId);
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

    @Override
    @Transactional
    public QuestionDto getQuestionWithOptions(final Long questionId) {
        Question question = questionRepository.getReferenceById(questionId);
        QuestionDto questionDto = questionMapper.mapToDto(question);

        handleAnswerDiscrepancy(questionDto, questionDto.getCategory());
        return questionDto;
    }

    private void handleAnswerDiscrepancy(final QuestionDto questionDto, final CategoryDto categoryDto) {
        Property questionsCount = propertyRepository.findByName(EXPRESS_QUIZ_DEFAULT_ANSWER_COUNT);
        List<AnswerDto> answers = questionDto.getAnswers();

        if (answers.size() < questionsCount.getIntValue()) {
            fillAnswerOptions(answers, categoryDto, questionsCount);
        }
    }

    private void fillAnswerOptions(List<AnswerDto> answers, CategoryDto categoryDto, Property questionsCount) {
        int answerAmountToAdd = questionsCount.getIntValue() - answers.size();
        String options = getAnswerOptions(answers);
        List<Long> glossaryIdList = getGlossaryIdList(answers);

        fetchWrongAnswerGlossaryList(categoryDto, options, answerAmountToAdd, glossaryIdList)
                .forEach(glossary -> answers.add(AnswerDto.withGlossary(glossary)));
    }

    private List<GlossaryDto> fetchWrongAnswerGlossaryList(
            final CategoryDto categoryDto, String options, int answerAmountToAdd, final List<Long> glossaryIdList) {
        if (Objects.nonNull(options)) {
            return glossaryService.getLimitedByOptionWithoutSelf(answerAmountToAdd, options, glossaryIdList);
        }
        return glossaryService.getLimitedByCategoryIdWithoutSelf(answerAmountToAdd, categoryDto.getCatId(), glossaryIdList);
    }

    private static List<Long> getGlossaryIdList(List<AnswerDto> answers) {
        return answers.stream()
                .map(AnswerDto::getGlossary)
                .map(GlossaryDto::getTermId)
                .collect(Collectors.toList());
    }

    private static String getAnswerOptions(List<AnswerDto> answers) {
        return answers.stream()
                .findFirst()
                .map(AnswerDto::getGlossary)
                .map(GlossaryDto::getOptions)
                .orElse(null);
    }
}
