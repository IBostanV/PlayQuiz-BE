package com.play.quiz.repository;

import com.play.quiz.model.Category;
import com.play.quiz.model.Glossary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GlossaryRepository extends JpaRepository<Glossary, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Glossary glos SET glos.isActive = CASE WHEN glos.isActive = TRUE THEN FALSE ELSE TRUE END WHERE glos.termId = :id")
    void toggleGlossary(@Param("id") final Long id);

    Optional<Glossary> findByKey(final String key);

    Optional<Glossary> findByCategory_catId(final Long categoryId);

    List<Glossary> findAllByCategory(final Category category);
}
