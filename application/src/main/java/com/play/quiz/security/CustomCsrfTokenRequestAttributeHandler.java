package com.play.quiz.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.function.Supplier;

@Component
public final class CustomCsrfTokenRequestAttributeHandler extends CsrfTokenRequestAttributeHandler {

    private SecureRandom secureRandom = new SecureRandom();

    public void setSecureRandom(SecureRandom secureRandom) {
        Assert.notNull(secureRandom, "secureRandom cannot be null");
        this.secureRandom = secureRandom;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       Supplier<CsrfToken> deferredCsrfToken) {
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        Assert.notNull(deferredCsrfToken, "deferredCsrfToken cannot be null");
        Supplier<CsrfToken> updatedCsrfToken = deferCsrfTokenUpdate(deferredCsrfToken);
        super.handle(request, response, updatedCsrfToken);
    }

    private Supplier<CsrfToken> deferCsrfTokenUpdate(Supplier<CsrfToken> csrfTokenSupplier) {
        return new CustomCsrfTokenRequestAttributeHandler.CachedCsrfTokenSupplier(() -> {
            CsrfToken csrfToken = csrfTokenSupplier.get();
            Assert.state(csrfToken != null, "csrfToken supplier returned null");
            String updatedToken = createXoredCsrfToken(this.secureRandom, csrfToken.getToken());
            return new DefaultCsrfToken(csrfToken.getHeaderName(), csrfToken.getParameterName(), updatedToken);
        });
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        String actualToken = super.resolveCsrfTokenValue(request, csrfToken);
        return getTokenValue(actualToken, csrfToken.getToken());
    }

    private static String getTokenValue(String actualToken, String token) {
        if (Arrays.equals(Utf8.encode(actualToken), Utf8.encode(token))) {
            return actualToken;
        }
        return null;
    }

    private static String createXoredCsrfToken(SecureRandom secureRandom, String token) {
        byte[] tokenBytes = Utf8.encode(token);
        byte[] randomBytes = new byte[tokenBytes.length];
        secureRandom.nextBytes(randomBytes);

        byte[] xoredBytes = xorCsrf(randomBytes, tokenBytes);
        byte[] combinedBytes = new byte[tokenBytes.length + randomBytes.length];
        System.arraycopy(randomBytes, 0, combinedBytes, 0, randomBytes.length);
        System.arraycopy(xoredBytes, 0, combinedBytes, randomBytes.length, xoredBytes.length);

        return Base64.getUrlEncoder().encodeToString(combinedBytes);
    }

    private static byte[] xorCsrf(byte[] randomBytes, byte[] csrfBytes) {
        int len = Math.min(randomBytes.length, csrfBytes.length);
        byte[] xoredCsrf = new byte[len];
        System.arraycopy(csrfBytes, 0, xoredCsrf, 0, csrfBytes.length);
        for (int i = 0; i < len; i++) {
            xoredCsrf[i] ^= randomBytes[i];
        }
        return xoredCsrf;
    }

    private static final class CachedCsrfTokenSupplier implements Supplier<CsrfToken> {

        private final Supplier<CsrfToken> delegate;

        private CsrfToken csrfToken;

        private CachedCsrfTokenSupplier(Supplier<CsrfToken> delegate) {
            this.delegate = delegate;
        }

        @Override
        public CsrfToken get() {
            if (this.csrfToken == null) {
                this.csrfToken = this.delegate.get();
            }
            return this.csrfToken;
        }

    }

}
