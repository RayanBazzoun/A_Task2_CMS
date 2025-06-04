package com.example.CMS.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DeterministicAesUtil {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String FIXED_IV = "1234567890abcdef";

    private final SecretKeySpec secretKey;
    private final IvParameterSpec ivSpec;

    public DeterministicAesUtil(String key) {
        if (key.length() != 16 && key.length() != 24 && key.length() != 32)
            throw new IllegalArgumentException("Key must be 16/24/32 chars long");
        this.secretKey = new SecretKeySpec(key.getBytes(), "AES");
        this.ivSpec = new IvParameterSpec(FIXED_IV.getBytes());
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        byte[] decoded = Base64.getDecoder().decode(encryptedText);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }
}
