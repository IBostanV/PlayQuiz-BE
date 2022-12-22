package com.play.quiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Q_VERIFICATION_TOKENS")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "verification_tokens_generator")
    @SequenceGenerator(name = "verification_tokens_generator", sequenceName = "verification_tokens_sequence", allocationSize = 1)
    private Long tokenId;
    private String token;
    @Column(name = "ISSUED_DATE")
    private LocalDateTime issuedDate;
    @Column(name = "VALIDITY_PERIOD")
    private Integer validityPeriod;
    @Setter @Column(name = "ACTIVATION_DATE")
    private LocalDateTime activationDate;
    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        VerificationToken that = (VerificationToken) o;
        return tokenId != null && Objects.equals(tokenId, that.tokenId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
