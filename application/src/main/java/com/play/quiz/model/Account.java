package com.play.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Q_USER")
@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "user_sequence", allocationSize = 1)
    private Long accountId;
    private String name;
    private String email;
    private String password;
    @Column(name = "IS_ENABLED")
    private boolean isEnabled;

    @SneakyThrows
    public Object clone() {
        return super.clone();
    }

    public void enable() { this.isEnabled = true; }

    public Account withId(final Long accountId) {
        this.accountId = accountId;
        return this;
    }
}
