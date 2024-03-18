package com.play.quiz.record;

import com.play.quiz.domain.helpers.BaseEntity;

import java.util.Collection;
import java.util.function.Function;

public record UserAdditionalInfo<T extends Collection<?>, E extends BaseEntity>(
        T collection, String sql, Long accountId, String key, Function<E, Long> entityIdFunction) {}
