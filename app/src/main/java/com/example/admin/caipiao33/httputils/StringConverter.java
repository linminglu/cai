package com.example.admin.caipiao33.httputils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by lsd on 2016/7/8.
 */

public class StringConverter implements Converter<ResponseBody, String>
{

    public static final StringConverter INSTANCE = new StringConverter();

    @Override
    public String convert(ResponseBody value) throws IOException
    {
        //        Gson gson = new Gson();
        //        BaseResponseBean baseResponseBean = gson.fromJson(value.string(), BaseResponseBean.class);
        //        String result1 = baseResponseBean.getResult();
        //        if (!StringUtils.isEmpty(result1))
        //        {
        //            String decrypt = P2PNative.getInstance().decrypt(result1);
        //            baseResponseBean.setResult(decrypt);
        //        }
        //        String result = gson.toJson(baseResponseBean, BaseResponseBean.class);
        //        KLog.e(result);
        return value.string();
    }
}
