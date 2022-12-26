package com.play.quiz.model.helpers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

@Getter
@ToString
@EqualsAndHashCode
public final class FieldMessagePair<S, T> {
        private final S field;

        private final T message;

        private FieldMessagePair(S field, T message) {
            Assert.notNull(field, "Field must not be null");
            Assert.notNull(message, "Message must not be null");

            this.field = field;
            this.message = message;
        }

        public static <S, T> FieldMessagePair<S, T> of(S field, T message) {
            return new FieldMessagePair<>(field, message);
        }
}
