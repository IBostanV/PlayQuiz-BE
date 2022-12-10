package com.play.quiz.model.helpers;

import com.play.quiz.dto.AccountDto;
import com.play.quiz.security.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@RequiredArgsConstructor
public class AccountInfo {
    private final Token token;
    private final AccountDto account;
}
