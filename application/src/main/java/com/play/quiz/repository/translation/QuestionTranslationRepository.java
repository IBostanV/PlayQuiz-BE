package com.play.quiz.repository.translation;

import com.play.quiz.model.translation.QuestionTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTranslationRepository extends JpaRepository<QuestionTranslation, Long> {
}
