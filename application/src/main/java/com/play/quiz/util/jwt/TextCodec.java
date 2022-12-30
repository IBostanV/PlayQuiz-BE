package com.play.quiz.util.jwt;

public interface TextCodec extends io.jsonwebtoken.impl.TextCodec {
    io.jsonwebtoken.impl.TextCodec BASE64 = new DefaultTextCodecFactory().getTextCodec();
    io.jsonwebtoken.impl.TextCodec BASE64URL = new Base64UrlCodec();
}
