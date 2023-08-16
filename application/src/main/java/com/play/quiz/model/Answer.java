package com.play.quiz.model;

import java.util.List;
import java.util.Objects;

import com.play.quiz.model.translation.AnswerTranslation;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "Q_ANSWERS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "answer_generator")
    @SequenceGenerator(name = "answer_generator", sequenceName = "answers_seq", allocationSize = 1)
    private Long ansId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QUESTION_ID")
    @ToString.Exclude
    private Question question;

    private String content;

    @OneToOne(targetEntity = Glossary.class)
    @JoinColumn(name = "TERM_ID")
    private Glossary glossary;

    @OneToMany(mappedBy = "answer",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    @ToString.Exclude
    private List<AnswerTranslation> answerTranslations;

    public void setQuestion(Question question) {
        this.question = question;
    }


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
}
