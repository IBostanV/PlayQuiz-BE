package com.play.quiz.repository;

import java.util.List;
import java.util.Optional;

import com.play.quiz.domain.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNaturalId(String naturalId);

    @Query("SELECT cat FROM Category cat WHERE cat.visible = TRUE")
    List<Category> findAllActive(Sort sort);
}
