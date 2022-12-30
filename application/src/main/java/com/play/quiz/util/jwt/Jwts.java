package com.play.quiz.util.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;

public final class Jwts {

    public static JwtParser parser() {
        return new DefaultJwtParser();
    }

    public static JwtBuilder builder() {
        return new DefaultJwtBuilder();
    }
}
