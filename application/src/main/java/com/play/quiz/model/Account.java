package com.play.quiz.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Account {
    Long id;
    String name;
    String email;
    String password;
}
