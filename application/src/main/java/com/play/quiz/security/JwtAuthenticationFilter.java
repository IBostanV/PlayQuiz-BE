package com.play.quiz.security;

import com.play.quiz.controller.RestEndpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.token.prefix:\"Bearer \"}")
    private static String JWT_TOKEN_PREFIX;

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !Objects.equals(request.getRequestURI(), RestEndpoint.CONTEXT_PATH);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        final String token = parseRequest(request);
        final String email = jwtProvider.getEmail(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        SecurityContextHolder.getContext().setAuthentication(createAuthentication(request, userDetails));
    }

    private Authentication createAuthentication(final HttpServletRequest request, final UserDetails userDetails) {
        final UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return token;
    }

    private String parseRequest(final HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(header -> header.startsWith(JWT_TOKEN_PREFIX))
                .map(header -> header.replace(JWT_TOKEN_PREFIX, ""))
                .orElse("");
    }
}
