package com.play.quiz.engine;

import com.play.quiz.enums.QuestionType;
import com.play.quiz.fixtures.CategoryFixture;
import com.play.quiz.fixtures.GlossaryFixture;
import com.play.quiz.fixtures.QuestionFixture;
import com.play.quiz.model.Category;
import com.play.quiz.model.Glossary;
import com.play.quiz.model.Question;
import com.play.quiz.repository.GlossaryRepository;
import com.play.quiz.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionGenerationEngineTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private GlossaryRepository glossaryRepository;

    @Captor
    private ArgumentCaptor<Category> categoryCaptor;

    @Captor
    private ArgumentCaptor<QuestionType> questionTypeArgumentCaptor;

    @Captor
    private ArgumentCaptor<Question> questionArgumentCaptor;

    @InjectMocks
    private QuestionGenerationEngine engine;

    @Test
    void given_saved_glossary_when_generateByAddedGlossary_then_return_generated_questions() {
        final Glossary glossary = GlossaryFixture.getGlossary();
        final List<Question> expectedResult = getGeneratedQuestionList(10L, glossary.getKey());
        final Question expectedEntityForSave = QuestionFixture.getGeneratedQuestion(null, "Life");

        when(questionRepository.findByTypeAndCategory(QuestionType.TEMPLATE, CategoryFixture.getCategory()))
                .thenReturn(QuestionFixture.getTemplateQuestionList());
        when(questionRepository.save(any())).thenReturn(
                getGeneratedQuestionList(10L, glossary.getKey()).get(0),
                getGeneratedQuestionList(10L, glossary.getKey()).get(1),
                getGeneratedQuestionList(10L, glossary.getKey()).get(2),
                getGeneratedQuestionList(10L, glossary.getKey()).get(3),
                getGeneratedQuestionList(10L, glossary.getKey()).get(4),
                getGeneratedQuestionList(10L, glossary.getKey()).get(5),
                getGeneratedQuestionList(10L, glossary.getKey()).get(6),
                getGeneratedQuestionList(10L, glossary.getKey()).get(7),
                getGeneratedQuestionList(10L, glossary.getKey()).get(8));

        List<Question> result = engine.generateByAddedGlossary(glossary);

        assertEquals(expectedResult, result);
        assertEquals(expectedResult.size(), result.size());

        verify(questionRepository, times(1)).findByTypeAndCategory(QuestionType.TEMPLATE, CategoryFixture.getCategory());
        verify(questionRepository, times(9)).save(any());

        verify(questionRepository).findByTypeAndCategory(questionTypeArgumentCaptor.capture(), categoryCaptor.capture());
        QuestionType questionTypeArgumentCaptorValue = questionTypeArgumentCaptor.getValue();
        assertEquals(QuestionType.TEMPLATE, questionTypeArgumentCaptorValue);

        Category categoryCaptorValue = categoryCaptor.getValue();
        assertEquals(CategoryFixture.getCategory(), categoryCaptorValue);

        verify(questionRepository, times(9)).save(questionArgumentCaptor.capture());
        Question questionArgumentCaptorValue = questionArgumentCaptor.getValue();
        assertTrue(Objects.isNull(questionArgumentCaptorValue.getQuestionId()));
        assertEquals(expectedEntityForSave.getType(), questionArgumentCaptorValue.getType());
        assertEquals(expectedEntityForSave.getCategory(), questionArgumentCaptorValue.getCategory());
        assertEquals(expectedEntityForSave.getAttributes(), questionArgumentCaptorValue.getAttributes());
    }

    @Test
    void given_saved_glossary_and_attribute_question_when_generateByAddedGlossary_return_generated_questions() {
        final Glossary glossary = GlossaryFixture.getGlossary();
        final List<Question> expectedResult = getGeneratedQuestionList(10L, glossary.getValue());
        final Question expectedEntityForSave = QuestionFixture.getGeneratedQuestion(null, "Life");

        when(questionRepository.findByTypeAndCategory(QuestionType.TEMPLATE, CategoryFixture.getCategory()))
                .thenReturn(QuestionFixture.getAttributeTemplateQuestionList());
        when(questionRepository.save(any())).thenReturn(
                getGeneratedQuestionList(10L, glossary.getValue()).get(0),
                getGeneratedQuestionList(10L, glossary.getValue()).get(1),
                getGeneratedQuestionList(10L, glossary.getValue()).get(2),
                getGeneratedQuestionList(10L, glossary.getValue()).get(3),
                getGeneratedQuestionList(10L, glossary.getValue()).get(4),
                getGeneratedQuestionList(10L, glossary.getValue()).get(5),
                getGeneratedQuestionList(10L, glossary.getValue()).get(6),
                getGeneratedQuestionList(10L, glossary.getValue()).get(7),
                getGeneratedQuestionList(10L, glossary.getValue()).get(8));

        List<Question> result = engine.generateByAddedGlossary(glossary);

        assertEquals(expectedResult, result);
        assertEquals(expectedResult.size(), result.size());

        verify(questionRepository, times(1)).findByTypeAndCategory(QuestionType.TEMPLATE, CategoryFixture.getCategory());
        verify(questionRepository, times(9)).save(any());

        verify(questionRepository).findByTypeAndCategory(questionTypeArgumentCaptor.capture(), categoryCaptor.capture());
        QuestionType questionTypeArgumentCaptorValue = questionTypeArgumentCaptor.getValue();
        assertEquals(QuestionType.TEMPLATE, questionTypeArgumentCaptorValue);

        Category categoryCaptorValue = categoryCaptor.getValue();
        assertEquals(CategoryFixture.getCategory(), categoryCaptorValue);

        verify(questionRepository, times(9)).save(questionArgumentCaptor.capture());
        Question questionArgumentCaptorValue = questionArgumentCaptor.getValue();
        assertTrue(Objects.isNull(questionArgumentCaptorValue.getQuestionId()));
        assertEquals(expectedEntityForSave.getType(), questionArgumentCaptorValue.getType());
        assertEquals(expectedEntityForSave.getCategory(), questionArgumentCaptorValue.getCategory());
        assertEquals(expectedEntityForSave.getAttributes(), questionArgumentCaptorValue.getAttributes());
    }

    @Test
    void given_saved_template_question_when_generateFromCreatedTemplate_then_return_generated_questions() {
        String meaningOfLife = "Power";
        final Question templateQuestion = QuestionFixture.getTemplateQuestion(1L);
        final List<Question> expectedResult = getGeneratedQuestionList(4L, "Life");
        final Question expectedEntityForSave = QuestionFixture.getGeneratedQuestion(null, "Life");

        when(glossaryRepository.findAllByCategory(templateQuestion.getCategory())).thenReturn(GlossaryFixture.getGlossaryList());
        when(questionRepository.save(any())).thenReturn(
                getGeneratedQuestionList(4L, meaningOfLife).get(0),
                getGeneratedQuestionList(4L, meaningOfLife).get(1),
                getGeneratedQuestionList(4L, meaningOfLife).get(2));

        List<Question> result = engine.generateFromCreatedTemplate(templateQuestion);

        assertEquals(expectedResult, result);
        assertEquals(expectedResult.size(), result.size());

        verify(glossaryRepository, times(1)).findAllByCategory(templateQuestion.getCategory());
        verify(questionRepository, times(3)).save(any());

        verify(glossaryRepository).findAllByCategory(categoryCaptor.capture());
        Category categoryCaptorValue = categoryCaptor.getValue();
        assertEquals(CategoryFixture.getCategory(), categoryCaptorValue);

        verify(questionRepository, times(3)).save(questionArgumentCaptor.capture());
        Question questionArgumentCaptorValue = questionArgumentCaptor.getValue();
        assertTrue(Objects.isNull(questionArgumentCaptorValue.getQuestionId()));
        assertEquals(expectedEntityForSave.getType(), questionArgumentCaptorValue.getType());
        assertEquals(expectedEntityForSave.getCategory(), questionArgumentCaptorValue.getCategory());
        assertEquals(expectedEntityForSave.getAttributes(), questionArgumentCaptorValue.getAttributes());
    }

    private static List<Question> getGeneratedQuestionList(final Long end, final String placeholder) {
        return LongStream.range(1, end)
                .mapToObj(id -> QuestionFixture.getGeneratedQuestion(id, placeholder))
                .collect(Collectors.toList());
    }
}
