package com.play.quiz.model;

import com.play.quiz.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Q_QUESTIONS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qstn_generator")
    @SequenceGenerator(name = "qstn_generator", sequenceName = "questions_seq", allocationSize = 1)
    private Long questionId;

    @OneToOne(targetEntity = Account.class)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Transient
    private Object tipId;

    @OneToOne(targetEntity = Category.class)
    @JoinColumn(name = "CAT_ID")
    private Category category;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @Column(name = "COMPLEXITY_LEVEL")
    private int complexityLevel;

    private String content;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @OneToOne(targetEntity = Account.class)
    @JoinColumn(name = "UPDATE_ACCOUNT_ID")
    private Account updatedAccount;

    private String topic;

    private int priority;

    private String attributes;

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH},
            orphanRemoval = true)
    @ToString.Exclude
    private List<Answer> answers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Question question = (Question) o;
        return questionId != null && Objects.equals(questionId, question.questionId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
