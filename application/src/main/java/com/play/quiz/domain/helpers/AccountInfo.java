package com.play.quiz.domain.helpers;

import com.play.quiz.dto.AccountDto;
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
    private final String jwtToken;
    private final AccountDto account;
}
