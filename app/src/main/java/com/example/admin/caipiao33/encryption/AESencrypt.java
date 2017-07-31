package com.example.admin.caipiao33.encryption;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * *AES 加密算法
 *
 * @author Caoxiangyu
 * @date 2015年10月23日 下午9:50:28
 */
public class AESencrypt
{
    // 秘钥
    public final static String PASSWORD = "0123456789ABCDEF";

    /**
     * AES加密
     */
    public static String encrypt(String seed, String cleartext) throws Exception
    {
        // 秘钥获取
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
    }

    /**
     * AES解密
     */
    public static String decrypt(String seed, String encrypted) throws Exception
    {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
    }

    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(byte[] seed) throws Exception
    {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = null;
        if (android.os.Build.VERSION.SDK_INT >= 17)
        {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        }
        else
        {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /**
     * 加密过程
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception
    {
        // IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivspec);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * 解密过程
     *
     * @param raw
     * @param encrypted
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception
    {
        // IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        // cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        ;
        return decrypted;
    }

    public static String toHex(String txt)
    {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex)
    {
        return new String(toByte(hex));
    }

    /**
     * 十六进制转换二进制
     *
     * @param hexString
     * @return
     */
    public static byte[] toByte(String hexString)
    {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    /**
     * 二进制转换十六进制
     *
     * @return
     */
    public static String toHex(byte[] buf)
    {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++)
        {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b)
    {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    /**
     * 本地与服务器AES加密
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encrypt2PHP(String key, String input)
    {
        if (TextUtils.isEmpty(input))
        {
            return null;
        }
        byte[] crypted = null;
        try
        {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey);
            crypted = cipher.doFinal(input.getBytes());
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return new String(Base64.encode(crypted, Base64.NO_WRAP));
    }

    /**
     * 本地与服务器AES解密方法
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decrypt2PHP(String key, String input)
    {
        if (TextUtils.isEmpty(input))
        {
            return null;
        }
        String result = null;
        try
        {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey);
            byte[] output = cipher.doFinal(Base64.decode(input, Base64.NO_WRAP));
            result = new String(output, "UTF-8");
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }
        return result;
    }
}