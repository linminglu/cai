package com.example.admin.caipiao33.encryption;


import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.P2PNative;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.UserConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateCode
{
    private static String BUSINESS_SECRET_KEY = "4CE19CA8FCD150A4";

    /**
     * Caoxiangyu 2015.10.23 从服务器的data中解密获取数据内容
     */
    public static String parseContent(String data)
    {
        String s = null;
        try
        {
            s = P2PNative.getInstance().decrypt(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Caoxiangyu 2015.12.24 加密给服务器的数据
     */
    public static String parseContent4Php(String data)
    {
        String s = null;
        try
        {
            s = P2PNative.getInstance().encrypt(data);
            //            s = URLDecoder.decode(s, "utf-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Caoxiangyu 2015.10.23 参数生成json
     */
    public static JSONObject parse2Json(HashMap<String, String> map)
    {
        JSONObject correct = new JSONObject();
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        for (int i = 0; i < infoIds.size(); i++)
        {
            String key = infoIds.get(i).getKey();
            String val = infoIds.get(i).getValue();
            try
            {
                correct.put(key, val);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        return correct;
    }

    /**
     * Caoxiangyu 2017.8.7 手机端生成传递给服务器的参数
     */
    public static HashMap<String, String> getParams(HashMap<String, String> map)
    {
        HashMap<String, String> params = new HashMap<>();
        if (map == null)
        {
            map = new HashMap<>();
        }
        String remembertoken = UserConfig.getInstance().getToken(MyApplication.getInstance());
        String token = map.remove("token");

        if (!StringUtils.isEmpty2(token))
        {
            remembertoken = token;
        }
        map.put("deviceType", Constants.MERCHANTID);
        map.put("token", StringUtils.isEmpty2(remembertoken) ? "" : remembertoken);

        params.put("data", parseContent4Php(parse2Json(map).toString()));  //传给服务器的参数
        return params;
    }

    /**
     * Caoxiangyu 2015.10.23 手机端RSA加密方法
     */
    //    public static String getRSAString(String context)
    //    {
    //        byte[] encryptByte = RSAUtils.encryptData(context.getBytes(), MyApplication.getInstance().getPublicKey());
    //        return Base64Utils.encode(encryptByte);
    //    }

    /**
     * Caoxiangyu 2015.10.23 手机端生成Sign发送给服务器的方法
     */
    public static String getSign(TreeMap<String, String> map)
    {
        try
        {
            if (map != null)
            {
                return getMD5Str32(BUSINESS_SECRET_KEY + "&" + getParameters(map) + "&" + BUSINESS_SECRET_KEY)
                        .toUpperCase();
            }
            else
            {
                return getMD5Str32(BUSINESS_SECRET_KEY + "&" + BUSINESS_SECRET_KEY).toUpperCase();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Caoxiangyu 2015.10.23 生成业务参数的方法
     */
    public static String getParameters(TreeMap<String, String> map)
    {
        if (map != null)
        {
            StringBuilder sb = new StringBuilder();
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>()
            {
                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2)
                {
                    return (o1.getKey().compareTo(o2.getKey()));
                }
            });
            for (int i = 0; i < infoIds.size(); i++)
            {
                String key = infoIds.get(i).getKey();
                String val = infoIds.get(i).getValue();
                sb.append(key).append("=");
                if (i != infoIds.size() - 1)
                {
                    sb.append(val).append("&");
                }
                else
                {
                    sb.append(val);
                }
            }
            return sb.toString();
        }
        else
        {
            return "";
        }

    }

    private static String replaceBlank(String str)
    {
        String dest = "";
        if (str != null)
        {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
    }


    /**
     * Caoxiangyu 2015.10.23 MD5加密方法
     */
    private static String getMD5Str16(String str)
    {
        MessageDigest messageDigest = null;

        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e)
        {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++)
        {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
            {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            }
            else
            {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        //16位加密，从第9位到25位
        return md5StrBuff.substring(8, 24).toString().toUpperCase();
    }

    /**
     * Caoxiangyu 2015.10.23 MD5加密方法
     */

    /**
     * MD5 32位加密方法一 小写
     *
     * @param s
     * @return
     */
    private final static String getMD5Str32(String s)
    {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try
        {
            byte[] strTemp = s.getBytes();
            //使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte b = md[i];
                //System.out.println((int)b);
                //将没个数(int)b进行双字节加密
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        }
        catch (Exception e)
        {
            return null;
        }
    }

}
