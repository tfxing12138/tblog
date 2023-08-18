package com.tfxing.tblog.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SymmetricDecryptionUtils {

    private static final String ALGORITHM = "AES";
    private static final String PADDING = "AES/ECB/PKCS5Padding"; // Using ECB for simplicity, not recommended for strong security

    public static String decrypt(String key, String encryptedContent) throws Exception {
        SecretKeySpec secretKeySpec = generateSecretKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
        return new String(decryptedBytes);
    }

    private static SecretKeySpec generateSecretKey(String key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, ALGORITHM);
    }

    public static void main(String[] args) {
        try {
            String key = "1714cc98e49340d9"; // Replace with your actual base64 encoded key
            String encryptedContent = "Link1234"; // Replace with your actual encrypted content

            /*String decrypted = AESUtil.encrypt(key, encryptedContent);
            System.out.println("Decrypted: " + decrypted);*/
            String decrypted = "yWr8pUQWfcR1g2aVKrYQqsQZIYldeGavVFo7vTUfAv0=";

            String decrypt = AESUtil.decrypt(key, decrypted);
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
