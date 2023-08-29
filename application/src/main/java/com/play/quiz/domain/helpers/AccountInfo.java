package com.play.quiz.domain.helpers;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.security.jwt.JwtToken;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode
public class AccountInfo {
    private final JwtToken jwtToken;
    private final AccountDto account;
}
