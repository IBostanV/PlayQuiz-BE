package com.play.quiz.repository;

import java.util.List;
import java.util.Optional;

import com.play.quiz.model.Category;
import com.play.quiz.model.Glossary;
import org.springframework.data.domain.Pageable;
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
            ", gl.value = :#{#glossary.value}" +
            ", gl.parent = :#{#glossary.parent}" +
            ", gl.options = :#{#glossary.options}" +
            ", gl.isActive = :#{#glossary.isActive}" +
            ", gl.category = :#{#glossary.category}" +
            " WHERE gl.termId = :#{#glossary.termId}")
    int saveWithoutAttachment(@Param("glossary") final Glossary glossary);

    Optional<Glossary> findByKey(final String key);

    List<Glossary> findAllByCategory(final Category category);

    Optional<List<Glossary>> findByCategory_catId(final Long categoryId);

    List<Glossary> findByCategory_catId(final Long categoryId, final Pageable pageable);

    @Query("SELECT gl FROM Glossary gl WHERE gl.category.catId = :categoryId" +
            " AND gl.termId NOT IN :rightAnswerIds")
    List<Glossary> findNoRightAnswersByCategory_catId(final Long categoryId, final Pageable pageable, List<Long> rightAnswerIds);

    @Query("SELECT gl FROM Glossary gl WHERE gl.options LIKE CONCAT('%', :option, '%')" +
            " AND gl.termId NOT IN :rightAnswerIds")
    List<Glossary> getByOption(final String option, final Pageable pageable, final List<Long> rightAnswerIds);

    @Query("SELECT gl FROM Glossary gl WHERE gl.options LIKE CONCAT('%', :option1, '%')" +
            " AND gl.options LIKE CONCAT('%', :option2, '%')")
    List<Glossary> getByOptions(final Pageable pageable, final String option1, final String option2);

    @Query("SELECT gl FROM Glossary gl" +
            " WHERE gl.options LIKE CONCAT('%', :option1, '%')" +
            " AND gl.options LIKE CONCAT('%', :option2, '%')" +
            " AND gl.options LIKE CONCAT('%', :option3, '%')")
    List<Glossary> getByOptions(final Pageable pageable, final String option1, final String option2, final String option3);
}
