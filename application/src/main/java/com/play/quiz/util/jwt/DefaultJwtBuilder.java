package com.play.quiz.util.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.JwtSigner;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;

@Slf4j
public class DefaultJwtBuilder extends io.jsonwebtoken.impl.DefaultJwtBuilder {
    @Override
    public JwtBuilder signWith(SignatureAlgorithm alg, String base64EncodedSecretKey) {
        Assert.hasText(base64EncodedSecretKey, "base64-encoded secret key cannot be null or empty.");
        Assert.isTrue(alg.isHmac(), "Base64-encoded key bytes may only be specified for HMAC signatures.  If using RSA or Elliptic Curve, use the signWith(SignatureAlgorithm, Key) method instead.");
        byte[] bytes = TextCodec.BASE64.decode(base64EncodedSecretKey);
        return signWith(alg, bytes);
    }

    @Override
    protected String base64UrlEncode(Object o, String errMsg) {
        try {
            return TextCodec.BASE64URL.encode(toJson(o));
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage());
            throw new IllegalStateException(errMsg, exception);
        }
    }

    protected JwtSigner createSigner(SignatureAlgorithm alg, Key key) {
        return new DefaultJwtSigner(alg, key);
    }
}
