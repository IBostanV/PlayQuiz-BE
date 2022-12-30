package com.play.quiz.util.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultSignatureValidatorFactory;
import io.jsonwebtoken.impl.crypto.SignatureValidator;
import io.jsonwebtoken.impl.crypto.SignatureValidatorFactory;
import io.jsonwebtoken.lang.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class DefaultJwtSignatureValidator extends io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator{

    private static final Charset US_ASCII = StandardCharsets.US_ASCII;

    private final SignatureValidator signatureValidator;

    public DefaultJwtSignatureValidator(SignatureAlgorithm alg, Key key) {
        this(DefaultSignatureValidatorFactory.INSTANCE, alg, key);
    }

    public DefaultJwtSignatureValidator(SignatureValidatorFactory factory, SignatureAlgorithm alg, Key key) {
        super(factory, alg, key);
        Assert.notNull(factory, "SignerFactory argument cannot be null.");
        this.signatureValidator = factory.createSignatureValidator(alg, key);
    }

    @Override
    public boolean isValid(String jwtWithoutSignature, String base64UrlEncodedSignature) {

        byte[] data = jwtWithoutSignature.getBytes(US_ASCII);

        byte[] signature = TextCodec.BASE64URL.decode(base64UrlEncodedSignature);

        return this.signatureValidator.isValid(data, signature);
    }
}
