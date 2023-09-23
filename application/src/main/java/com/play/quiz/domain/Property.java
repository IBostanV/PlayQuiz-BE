package com.play.quiz.domain;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;

@Entity
@Table(name = "Q_PROPERTY")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prop_generator")
    @SequenceGenerator(name = "prop_generator", sequenceName = "prop_sequence", allocationSize = 1)
    private Long propertyId;
    private String name;
    private String value;

    @Column(name = "OLD_VALUE")
    private String oldValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Property that = (Property) o;
        return propertyId != null && Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public Long getLongValue() {
        return Long.parseLong(this.value);
    }

    public Integer getIntValue() {
        return Integer.parseInt(this.value);
    }
}
