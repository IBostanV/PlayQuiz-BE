package com.play.quiz.repository;

import java.util.List;
import java.util.Optional;

import com.play.quiz.model.Category;
import com.play.quiz.model.Glossary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GlossaryRepository extends JpaRepository<Glossary, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Glossary glos " +
            " SET glos.isActive = CASE WHEN glos.isActive = TRUE THEN FALSE ELSE TRUE END" +
            " WHERE glos.termId = :id")
    int toggleGlossary(@Param("id") final Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Glossary glos " +
            " SET glos.key = :#{#glossary.key}" +
            ", glos.value = :#{#glossary.value}" +
            ", glos.parent = :#{#glossary.parent}" +
            ", glos.options = :#{#glossary.options}" +
            ", glos.isActive = :#{#glossary.isActive}" +
            ", glos.category = :#{#glossary.category}" +
            " WHERE glos.termId = :#{#glossary.termId}")
    int saveWithoutAttachment(@Param("glossary") final Glossary glossary);

    Optional<Glossary> findByKey(final String key);

    List<Glossary> findAllByCategory(final Category category);

    Optional<List<Glossary>> findByCategory_catId(final Long categoryId);
}
