package com.play.quiz.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "Q_TROPHIES")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Trophy {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trophy_generator")
    @SequenceGenerator(name = "trophy_generator", sequenceName = "trophies_seq", allocationSize = 1)
    private Long trophyId;

    private String name;

    @Lob
    private byte[] attachment;

    private String options;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Trophy trophy = (Trophy) o;
        return trophyId != null && Objects.equals(trophyId, trophy.trophyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
