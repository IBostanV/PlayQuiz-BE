package com.play.quiz.domain;

import java.time.LocalDateTime;
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
import org.hibernate.proxy.HibernateProxy;

@Entity
@Table(name = "Q_MESSAGE")
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_generator")
    @SequenceGenerator(name = "message_generator", sequenceName = "message_seq", allocationSize = 1)
    @Column(name = "MSG_ID")
    private Long messageId;
    private String source;
    private String content;
    private String sessionId;
    private byte[] attachment;
    private String destination;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Message message = (Message) o;
        return getMessageId() != null && Objects.equals(getMessageId(), message.getMessageId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
