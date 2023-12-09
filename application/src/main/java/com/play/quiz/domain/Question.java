package com.play.quiz.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.play.quiz.converter.AttributeListConverter;
import com.play.quiz.enums.QuestionAttribute;
import com.play.quiz.enums.QuestionType;
import com.play.quiz.domain.translation.QuestionTranslation;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "Q_QUESTION")
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

    @OneToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    @ToString.Exclude
    private Account account;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Transient
    private Object tipId;

    @OneToOne(targetEntity = Category.class)
    @JoinColumn(name = "CAT_ID")
    private Category category;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "COMPLEXITY_LEVEL")
    private int complexityLevel;

    private String content;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATE_ACCOUNT_ID")
    @ToString.Exclude
    private Account updatedAccount;
    private String topic;
    private int priority;

    @Convert(converter = AttributeListConverter.class)
    private List<QuestionAttribute> attributes;

    @Setter
    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH})
    @ToString.Exclude
    private List<Answer> answers;

    @OneToMany(mappedBy = "question",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @ToString.Exclude
    private List<QuestionTranslation> translations;

    public void setUpdatedAccount(Account updatedAccount) {
        this.updatedAccount = updatedAccount;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Question copy(final QuestionType questionType, String content) {
        return copy(questionType, content, Collections.emptyList());
    }

    public Question copy(final QuestionType questionType, String content, final List<QuestionAttribute> attributes) {
        return new Question(null, this.account, questionType, this.tipId, this.category, this.isActive,
                this.complexityLevel, content, this.createdDate, this.updatedDate, this.updatedAccount,
                this.topic, this.priority, attributes, this.answers, this.translations);
    }

    public void fillTranslationsParent() {
        this.translations.forEach(translation -> translation.setQuestion(this));
    }

    public void fillAnswersParent() {
        this.answers.forEach(answer -> answer.setQuestion(this));
    }

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
