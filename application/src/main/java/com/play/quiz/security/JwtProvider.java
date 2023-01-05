package com.play.quiz.security;

import com.play.quiz.util.jwt.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    public String getUsernameFromToken(final String token) {
        Assert.hasText(token, "Authentication token can not be empty.");

        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public JwtToken generate(final Authentication authentication) {
        return new JwtToken(generateToken(authentication));
    }

    private String generateToken(final Authentication authentication) {
        final User principal = (User)authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
