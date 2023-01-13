package com.play.quiz.model;

import com.play.quiz.converter.QuestionIdsConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Q_QUIZZES")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_generator")
    @SequenceGenerator(name = "quiz_generator", sequenceName = "quizzes_seq", allocationSize = 1)
    private Long quizId;

    @OneToOne(targetEntity = Category.class)
    @JoinColumn(name = "CAT_ID")
    private Category category;

    @Column(name = "QUESTION_IDS")
    @Convert(converter = QuestionIdsConverter.class)
    private Set<Long> questionIds;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @Column(name = "QUESTIONS_COUNT")
    private int questionsCount;

    @Transient
    private List<Question> questionList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Quiz quiz = (Quiz) o;
        return quizId != null && Objects.equals(quizId, quiz.quizId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
