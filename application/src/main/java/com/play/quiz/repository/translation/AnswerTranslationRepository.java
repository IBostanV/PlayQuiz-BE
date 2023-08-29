package com.play.quiz.repository.translation;

import com.play.quiz.domain.translation.AnswerTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerTranslationRepository extends JpaRepository<AnswerTranslation, Long> {
}
