package com.play.quiz.repository.translation;

import com.play.quiz.model.translation.CategoryTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, Long> {
}
