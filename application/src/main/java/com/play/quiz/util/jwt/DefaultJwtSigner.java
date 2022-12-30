package com.play.quiz.util.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultSignerFactory;
import io.jsonwebtoken.impl.crypto.Signer;
import io.jsonwebtoken.impl.crypto.SignerFactory;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class DefaultJwtSigner extends io.jsonwebtoken.impl.crypto.DefaultJwtSigner {
    private final Signer signer;

    public DefaultJwtSigner(SignatureAlgorithm alg, Key key) {
        this(DefaultSignerFactory.INSTANCE, alg, key);
    }

    public DefaultJwtSigner(SignerFactory factory, SignatureAlgorithm alg, Key key) {
        super(factory, alg, key);
        this.signer = factory.createSigner(alg, key);
    }

    @Override
    public String sign(String jwtWithoutSignature) {
        byte[] bytesToSign = jwtWithoutSignature.getBytes(StandardCharsets.US_ASCII);
        byte[] signature = signer.sign(bytesToSign);

        return TextCodec.BASE64URL.encode(signature);
    }
}
