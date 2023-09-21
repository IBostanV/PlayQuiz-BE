package com.play.quiz.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ROLE_ADMIN(1L),
    ROLE_USER(2L);

    private final Long roleId;
}
