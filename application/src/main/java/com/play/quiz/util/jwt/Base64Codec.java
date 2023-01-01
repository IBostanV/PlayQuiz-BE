package com.play.quiz.util.jwt;

public class Base64Codec extends io.jsonwebtoken.impl.Base64Codec {

    public String encode(byte[] data) {
        return jakarta.xml.bind.DatatypeConverter.printBase64Binary(data);
    }

    @Override
    public byte[] decode(String encoded) {
        return jakarta.xml.bind.DatatypeConverter.parseBase64Binary(encoded);
    }
}
