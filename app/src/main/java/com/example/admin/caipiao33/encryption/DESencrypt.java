package com.example.admin.caipiao33.encryption;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by admin on 2015/10/26.
 */
public class DESencrypt
{
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static String encode(String key, String data) throws Exception
    {
        return encode(key, data.getBytes());
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    public static String encode(String key, byte[] data) throws Exception
    {
        try
        {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            return Base64Utils.encode(bytes);
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @param key  解密私钥，长度不能够小于8位
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public static String decode(String key, byte[] data) throws Exception
    {
        try
        {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return Base64Utils.encode(cipher.doFinal(data));
        }
        catch (Exception e)
        {
            throw new Exception(e);
        }
    }
}