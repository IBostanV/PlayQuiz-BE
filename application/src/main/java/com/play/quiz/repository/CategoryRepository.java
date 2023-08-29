package com.play.quiz.repository;

import java.util.Optional;

import com.play.quiz.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNaturalId(final String naturalId);
}
