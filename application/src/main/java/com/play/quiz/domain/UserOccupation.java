package com.play.quiz.domain;

import com.play.quiz.domain.helpers.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Q_OCCUPATION")
@Getter
@SuperBuilder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserOccupation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "occupation_generator")
    @SequenceGenerator(name = "occupation_generator", sequenceName = "occupation_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String domain;
    @OneToOne(targetEntity = UserOccupation.class)
    @JoinColumn(name = "PARENT_ID")
    private UserOccupation parent;
    private String status;
}
