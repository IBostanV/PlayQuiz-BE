package com.play.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

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

    @ManyToOne(targetEntity = Question.class)
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    private String content;

    @OneToOne(targetEntity = Glossary.class)
    @JoinColumn(name = "TERM_ID")
    private Glossary glossary;

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
