package com.play.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;

@Value
@Builder
public class AccountDto {
    Long id;
    String name;
    @Email(regexp = ".+@.+\\..+", message="Please provide a valid email address")
    String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;
}
