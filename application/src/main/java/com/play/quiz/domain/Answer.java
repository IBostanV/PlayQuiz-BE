package com.play.quiz.domain;

import java.util.List;
import java.util.Objects;

import com.play.quiz.domain.helpers.BaseEntity;
import com.play.quiz.domain.translation.AnswerTranslation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

@Entity
@Table(name = "Q_ANSWER")
@Getter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answers_seq", allocationSize = 1)
    private Long ansId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @ToString.Exclude
    private Question question;

    @Setter
    private String content;

    @OneToOne(targetEntity = Glossary.class)
    @JoinColumn(name = "TERM_ID")
    private Glossary glossary;

    @OneToMany(mappedBy = "answer",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private List<AnswerTranslation> answerTranslations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Answer answer = (Answer) o;
        return ansId != null && Objects.equals(ansId, answer.ansId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Long getId() {
        return this.ansId;
    }
}
