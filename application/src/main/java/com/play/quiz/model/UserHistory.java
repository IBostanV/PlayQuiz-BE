package com.play.quiz.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Q_USER_HISTORIES")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_generator")
    @SequenceGenerator(name = "history_generator", sequenceName = "user_history_seq", allocationSize = 1)
    private Long historyId;

    @OneToOne(targetEntity = Account.class)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne(targetEntity = Quiz.class)
    @JoinColumn(name = "QUIZ_ID")
    private Quiz quiz;

    @Lob
    @Column(name = "ANSWERS_JSON")
    private String answersJson;

    @Column(name = "COMPLETED_DATE")
    private LocalDateTime completedDate;

    @Column(name = "SPENT_TIME")
    private Double spentTime;

    @ManyToMany
    @JoinTable(name = "Q_HISTORY_TROPHY",
            joinColumns = @JoinColumn(name = "HISTORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "TROPHY_ID"))
    @ToString.Exclude
    private Set<Trophy> trophies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserHistory that = (UserHistory) o;
        return historyId != null && Objects.equals(historyId, that.historyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
