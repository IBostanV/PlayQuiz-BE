package com.play.quiz.service.impl;

import static com.play.quiz.util.Constant.DEFAULT_EXPRESS_QUESTIONS_COUNT;
import static com.play.quiz.util.Constant.EXPRESS;
import static com.play.quiz.util.Constant.EXPRESS_QUIZ_DEFAULT_ANSWER_COUNT;
import static com.play.quiz.util.Constant.EXPRESS_QUIZ_DEFAULT_TIME_SECONDS;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.play.quiz.dto.AnswerDto;
import com.play.quiz.dto.CategoryDto;
import com.play.quiz.dto.GlossaryDto;
import com.play.quiz.dto.QuestionDto;
import com.play.quiz.dto.QuizDto;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.mapper.QuizMapper;
import com.play.quiz.domain.Category;
import com.play.quiz.domain.Property;
import com.play.quiz.domain.Question;
import com.play.quiz.domain.Quiz;
import com.play.quiz.repository.PropertyRepository;
import com.play.quiz.repository.QuizRepository;
import com.play.quiz.service.CategoryService;
import com.play.quiz.service.GlossaryService;
import com.play.quiz.service.QuestionService;
import com.play.quiz.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizMapper quizMapper;
    private final QuizRepository quizRepository;
    private final QuestionMapper questionMapper;
    private final QuestionService questionService;
    private final CategoryService categoryService;
    private final GlossaryService glossaryService;
    private final PropertyRepository propertyRepository;

    @Override
    public QuizDto create(final QuizDto quizDto) {
        List<QuestionDto> questionsByCategory = questionService.findByCategory(getCategory(quizDto));
        List<Question> questions = questionMapper.mapToEntityList(questionsByCategory);
        handleQuestionDiscrepancy(quizDto, questions);
        Quiz quiz = quizRepository.save(buildQuiz(questions, quizDto));

        return quizMapper.toDto(quiz);
    }

    private static void handleQuestionDiscrepancy(final QuizDto quizDto, final List<Question> questions) {
        while (questions.size() < quizDto.getQuestionsCount()) {
            Collections.shuffle(questions);
            questions.add(questions.get(0));
        }
    }

    private Quiz buildQuiz(final List<Question> questionList, final QuizDto quizDto) {
        Collections.shuffle(questionList);
        List<Question> quizQuestions = questionList.subList(0, quizDto.getQuestionsCount());

        return Quiz.builder()
                .questionList(questionList)
                .category(getCategory(quizDto))
                .questionsCount(quizQuestions.size())
                .createdDate(quizDto.getCreatedDate())
                .questionIds(getQuestionIds(quizQuestions))
                .build();
    }

    private static Category getCategory(final QuizDto quizDto) {
        return Category.builder()
                .catId(quizDto.getCategory().getCatId())
                .name(quizDto.getCategory().getName())
                .naturalId(quizDto.getCategory().getNaturalId())
                .build();
    }

    private static Set<Long> getQuestionIds(final List<Question> quizQuestions) {
        return quizQuestions.stream()
                .map(Question::getQuestionId)
                .collect(Collectors.toSet());
    }

    @Override
    public QuizDto getById(final Long quizId) {
        Quiz quiz = quizRepository.getReferenceById(quizId);
        return quizMapper.toDto(quiz);
    }

    @Override
    @Transactional
    public QuizDto getExpressQuiz() {
        Property questionsCount = propertyRepository.findByName(DEFAULT_EXPRESS_QUESTIONS_COUNT);
        CategoryDto category = categoryService.getByNaturalId(EXPRESS);
        List<QuestionDto> questions = getReadyQuestions(questionsCount.getIntValue());

        return createQuiz(questionsCount.getIntValue(), category, questions);
    }

    private List<QuestionDto> getReadyQuestions(int questionCount) {
        List<Question> questions = questionService.getGeneralKnowledgeQuestions(questionCount);
        List<QuestionDto> questionDtoList = questionMapper.mapToDtoList(questions);
        questionDtoList.forEach(question -> {
            List<AnswerDto> answers = question.getAnswers();
            handleAnswerDiscrepancy(answers, question.getCategory());
        });

        return questionDtoList;
    }

    private void handleAnswerDiscrepancy(final List<AnswerDto> answers, final CategoryDto categoryDto) {
        Property questionsCount = propertyRepository.findByName(EXPRESS_QUIZ_DEFAULT_ANSWER_COUNT);
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

    private QuizDto createQuiz(int questionCount, final CategoryDto category, final List<QuestionDto> questionList) {
        Property quizTime = propertyRepository.findByName(EXPRESS_QUIZ_DEFAULT_TIME_SECONDS);

        return QuizDto.builder()
                .category(category)
                .questionList(questionList)
                .questionsCount(questionCount)
                .createdDate(LocalDateTime.now())
                .quizTime(quizTime.getIntValue())
                .questionIds(getQuestionIds(questionMapper.mapToEntityList(questionList)))
                .build();
    }
}
