package com.play.quiz.util;

import static com.play.quiz.util.Constant.ENCRYPTION_ALGORITHM;
import static com.play.quiz.util.Constant.ENCRYPTION_SECRET_KEY;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.play.quiz.domain.Property;
import com.play.quiz.repository.PropertyRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class EncryptionUtils {

    private final PropertyRepository propertyRepository;

    @SneakyThrows
    @Transactional
    public String encryptData(String data) {
        Property encryptionSecretKey = propertyRepository.findByName(ENCRYPTION_SECRET_KEY);
        Property encryptionAlgorithm = propertyRepository.findByName(ENCRYPTION_ALGORITHM);

        SecretKeySpec secretKey = new SecretKeySpec(encryptionSecretKey.getValue().getBytes(), "AES");
        Cipher cipher = Cipher.getInstance(encryptionAlgorithm.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
