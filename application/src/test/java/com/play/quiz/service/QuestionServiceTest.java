package com.play.quiz.service;

import static com.play.quiz.fixtures.QuestionFixture.getQuestionDtoWithTranslations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import com.play.quiz.dto.QuestionDto;
import com.play.quiz.engine.QuestionGenerationEngine;
import com.play.quiz.fixtures.AnswerFixture;
import com.play.quiz.fixtures.GlossaryFixture;
import com.play.quiz.fixtures.QuestionFixture;
import com.play.quiz.fixtures.QuestionTranslationFixture;
import com.play.quiz.mapper.QuestionMapper;
import com.play.quiz.mapper.QuestionMapperImpl;
import com.play.quiz.domain.Question;
import com.play.quiz.repository.GlossaryRepository;
import com.play.quiz.repository.QuestionRepository;
import com.play.quiz.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock private QuestionMapper questionMapper = new QuestionMapperImpl();
    @Mock private QuestionRepository questionRepository;
    @Mock private QuestionGenerationEngine generationEngine;
    @Mock private GlossaryRepository glossaryRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Captor
    ArgumentCaptor<Question> questionCaptor;

    @Test
    void given_questionDto_when_save_then_question_translations_have_question_set() {
        // Given
        QuestionDto questionDto = QuestionFixture.getQuestionDto();
        Question question = QuestionFixture.getQuestion();

        when(questionMapper.mapToEntity(any())).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.mapToDto(question)).thenReturn(getQuestionDtoWithTranslations());
        when(glossaryRepository.getReferenceById(GlossaryFixture.getGlossary().getTermId()))
                .thenReturn(GlossaryFixture.getGlossary());

        // When
        QuestionDto result = questionService.save(questionDto);

        // Then
        assertEquals(Collections.singletonList(QuestionTranslationFixture.getQuestionTranslationDto()), result.getTranslations());

        verify(questionRepository).save(questionCaptor.capture());
        Question captorValue = questionCaptor.getValue();

        captorValue.getTranslations().forEach(translation -> assertNotNull(translation.getQuestion()));
    }

    @Test
    void given_questionDto_when_save_then_question_answers_have_question_set() {
        // Given
        String glossaryValue = "2FqPOr34G";
        QuestionDto questionDto = QuestionFixture.getQuestionDto();
        Question question = QuestionFixture.getQuestion();

        when(questionMapper.mapToEntity(any())).thenReturn(question);
        when(questionRepository.save(question)).thenReturn(question);
        when(questionMapper.mapToDto(question)).thenReturn(getQuestionDtoWithTranslations());
        when(glossaryRepository.getReferenceById(GlossaryFixture.getGlossary().getTermId()))
                .thenReturn(GlossaryFixture.defaultGlossary("Key", glossaryValue));

        // When
        QuestionDto result = questionService.save(questionDto);

        // Then
        assertEquals(Collections.singletonList(AnswerFixture.getAnswerDtoNoQuestion()), result.getAnswers());

        verify(questionRepository).save(questionCaptor.capture());
        Question captorValue = questionCaptor.getValue();

        captorValue.getAnswers().forEach(answer -> {
            assertNotNull(answer.getQuestion());
            assertEquals(glossaryValue, answer.getContent());
        });
    }
}
