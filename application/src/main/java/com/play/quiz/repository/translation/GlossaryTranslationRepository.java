package com.play.quiz.repository.translation;

import com.play.quiz.model.translation.GlossaryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlossaryTranslationRepository extends JpaRepository<GlossaryTranslation, Long> {
}
