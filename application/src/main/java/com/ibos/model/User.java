package com.ibos.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Integer id;
    String name;
    String email;
    String password;
}
