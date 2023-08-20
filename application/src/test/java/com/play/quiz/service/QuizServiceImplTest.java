package com.play.quiz.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.dto.QuizDto;
import com.play.quiz.fixtures.CategoryFixture;
import com.play.quiz.fixtures.QuestionFixture;
import com.play.quiz.fixtures.QuizFixture;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.mapper.QuizMapper;
import com.play.quiz.mapper.QuizMapperImpl;
import com.play.quiz.model.Question;
import com.play.quiz.model.Quiz;
import com.play.quiz.repository.PropertyRepository;
import com.play.quiz.repository.QuizRepository;
import com.play.quiz.service.impl.QuizServiceImpl;
import com.play.quiz.util.EncryptionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuizServiceImplTest {

    @Mock
    private QuizRepository quizRepository;

    @Mock
    private QuestionService questionService;

    @Mock
    private QuestionMapper questionMapper;

    @Mock
    private CategoryService categoryService;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private GlossaryService glossaryService;

    private QuizService quizService;

    @BeforeEach
    void init() {
        QuizMapper quizMapper = new QuizMapperImpl();
        EncryptionUtils encryptionUtils = new EncryptionUtils(propertyRepository);
        quizService = new QuizServiceImpl(quizMapper, quizRepository, questionMapper, questionService,
                categoryService, glossaryService, propertyRepository);
    }

    @Test
    void given_quizDto_when_create_then_return_quiz_with_questions() {
        Quiz quiz = QuizFixture.getQuiz();
        QuizDto quizDto = QuizFixture.getQuizNoQuestionDto();
        QuestionDto questionDto = QuestionFixture.getQuestionDto();
        List<QuestionDto> questionDtos = Collections.singletonList(questionDto);
        Question question = QuestionFixture.getNoAnswerQuestion(1L, "Life");

        when(questionService.findByCategory(CategoryFixture.getSimpleCategory())).thenReturn(questionDtos);
        when(questionMapper.mapToEntityList(questionDtos)).thenReturn(new ArrayList<>(Collections.singletonList(question)));
        when(quizRepository.save(any())).thenReturn(quiz);

        QuizDto result = quizService.create(quizDto);

        assertEquals(1L, result.getQuizId());
        assertEquals(5, result.getQuestionsCount());
        assertEquals(Collections.singleton(1L), result.getQuestionIds());
        assertEquals(CategoryFixture.getCategoryDto(), result.getCategory());
        assertEquals(5, result.getQuestionList().size());
        assertTrue(result.getQuestionList().contains(QuestionFixture.getQuestionDto()));

        verify(questionService, only()).findByCategory(CategoryFixture.getSimpleCategory());
        verify(quizRepository, only()).save(any());
    }
}
