package com.example.admin.caipiao33.encryption;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 256位的AES加密解密方法，适配IOS与PHP
 *
 * @author Caoxiangyu
 * @date 2015年10月26日 下午5:50:28
 */
public class MyAESencrypt
{
    public MyAESencrypt()
    {
    }

    public static AlgorithmParameterSpec getIV()
    {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public static String encrypt(String key, String plainText) throws Exception
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(key.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec skey = new SecretKeySpec(digest.digest(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skey, getIV());
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64Utils.encode(encrypted);
    }

    public static String decrypt(String key, String cryptedText) throws Exception
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(key.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec skey = new SecretKeySpec(digest.digest(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, skey, getIV());
        byte[] bytes = Base64Utils.decode(cryptedText);
        byte[] decrypted = cipher.doFinal(bytes);
        return new String(decrypted, "UTF-8");
    }
}