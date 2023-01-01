package com.play.quiz.util.jwt;

public class DefaultTextCodecFactory extends io.jsonwebtoken.impl.DefaultTextCodecFactory{

    @Override
    public io.jsonwebtoken.impl.TextCodec getTextCodec() {
        return new Base64Codec();
    }
}
