package com.tfxing.tblog.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;


public class AESUtil {

    /**
     * 加密
     */
    // 加密:
    public static String encrypt(String secret, String value) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] key = secret.getBytes();
        byte[] input = value.getBytes("UTF-8");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        // CBC模式需要生成一个16 bytes的initialization vector:
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        byte[] data = cipher.doFinal(input);
        // IV不需要保密，把IV和密文一起返回:
        return join(iv, data);
    }


    /**
     * 解密
     */
    // 解密:
    public static String decrypt(String secret, String value) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] key = secret.getBytes();
        byte[] input = Base64.getDecoder().decode(value);

        // 把input分割成IV和密文:
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);
        System.arraycopy(input, 16, data, 0, data.length);
        // 解密:
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivps = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        byte[] bytes = cipher.doFinal(data);
        return new String(bytes, "UTF-8");
    }

    public static String join(byte[] bs1, byte[] bs2) {
        byte[] r = new byte[bs1.length + bs2.length];
        System.arraycopy(bs1, 0, r, 0, bs1.length);
        System.arraycopy(bs2, 0, r, bs1.length, bs2.length);
        return Base64.getEncoder().encodeToString(r);
    }

    private static final String ALGORITHM = "AES";
    private static final String PADDING = "AES/ECB/PKCS5Padding"; // Using ECB for simplicity, not recommended for strong security
    private static final int KEY_LENGTH = 128;

    public static String encrypt1(String key, String content) throws Exception {
        SecretKeySpec secretKeySpec = generateSecretKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        byte[] encryptedBytes = cipher.doFinal(content.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt1(String key, String encryptedContent) throws Exception {
        SecretKeySpec secretKeySpec = generateSecretKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedContent));
        return new String(decryptedBytes);
    }

    private static SecretKeySpec generateSecretKey(String key) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), key.getBytes(), 65536, KEY_LENGTH);
        SecretKey secretKey = factory.generateSecret(spec);

        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
    }
}
