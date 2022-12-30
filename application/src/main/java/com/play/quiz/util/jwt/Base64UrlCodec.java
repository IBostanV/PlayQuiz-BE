package com.play.quiz.util.jwt;

public class Base64UrlCodec extends io.jsonwebtoken.impl.Base64UrlCodec {

    @Override
    public String encode(byte[] data) {
        String base64Text = TextCodec.BASE64.encode(data);
        byte[] bytes = base64Text.getBytes(US_ASCII);

        bytes = removePadding(bytes);

        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '+') {
                bytes[i] = '-';
            } else if (bytes[i] == '/') {
                bytes[i] = '_';
            }
        }

        return new String(bytes, US_ASCII);
    }

    @Override
    public byte[] decode(String encoded) {
        char[] chars = encoded.toCharArray();

        chars = ensurePadding(chars);

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                chars[i] = '+';
            } else if (chars[i] == '_') {
                chars[i] = '/';
            }
        }

        String base64Text = new String(chars);

        return TextCodec.BASE64.decode(base64Text);
    }
}
