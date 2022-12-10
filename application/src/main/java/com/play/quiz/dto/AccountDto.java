package com.play.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@Builder
public class AccountDto {
    @NonFinal
    Long id;
    String name;
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
