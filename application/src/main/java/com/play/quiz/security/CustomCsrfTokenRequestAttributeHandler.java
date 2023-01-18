package com.play.quiz.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Supplier;

@Component
public final class CustomCsrfTokenRequestAttributeHandler extends CsrfTokenRequestAttributeHandler {
    private final XorCsrfTokenRequestAttributeHandler requestAttributeHandler = new XorCsrfTokenRequestAttributeHandler();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> deferredCsrfToken) {
        requestAttributeHandler.handle(request, response, deferredCsrfToken);
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        String actualToken = super.resolveCsrfTokenValue(request, csrfToken);
        if (Objects.nonNull(actualToken) && Objects.equals(actualToken, csrfToken.getToken())) {
            return actualToken;
        }
        return requestAttributeHandler.resolveCsrfTokenValue(request, csrfToken);
    }
}
