package com.play.quiz.repository;

import java.util.List;
import java.util.Optional;

import com.play.quiz.domain.Category;
import com.play.quiz.domain.Glossary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GlossaryRepository extends JpaRepository<Glossary, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Glossary gl " +
            " SET gl.isActive = CASE WHEN gl.isActive = TRUE THEN FALSE ELSE TRUE END" +
            " WHERE gl.termId = :id")
    int toggleGlossary(@Param("id") final Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Glossary gl " +
            " SET gl.key = :#{#glossary.key}" +
            ", gl.type = :#{#glossary.type}" +
            ", gl.value = :#{#glossary.value}" +
            ", gl.parent = :#{#glossary.parent}" +
            ", gl.options = :#{#glossary.options}" +
            ", gl.isActive = :#{#glossary.isActive}" +
            ", gl.category = :#{#glossary.category}" +
            " WHERE gl.termId = :#{#glossary.termId}")
    int saveWithoutAttachment(@Param("glossary") final Glossary glossary);

    Optional<Glossary> findByKey(String key);

    List<Glossary> findAllByCategory(final Category category);

    @Query(value = "SELECT qg.*" +
            " FROM q_glossary qg" +
            " WHERE cat_id IN (SELECT cat_id " +
            "                  FROM q_category " +
            "                  START WITH cat_id = :categoryId" +
            "                  CONNECT BY PRIOR cat_id = subcategory_id)",
            nativeQuery = true)
    Optional<List<Glossary>> findHierarchicalByCategoryId(final Long categoryId);
}
