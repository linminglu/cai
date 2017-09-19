package com.example.admin.caipiao33.httputils;

import com.socks.library.KLog;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by lsd on 2016/7/8.
 */

public class MyGsonConverterFactory extends Converter.Factory
{
    public static final MyGsonConverterFactory INSTANCE = new MyGsonConverterFactory();

    public static MyGsonConverterFactory create()
    {
        return INSTANCE;
    }

    // 我们只关实现从ResponseBody 到 String 的转换，所以其它方法可不覆盖
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit)
    {
        if (type == String.class)
        {
            return StringConverter.INSTANCE;
        }
        //其它类型我们不处理，返回null就行
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit)
    {
        KLog.e("124", "requestBodyConverter");
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit)
    {
        KLog.e("125", "stringConverter");
        return super.stringConverter(type, annotations, retrofit);
    }
}
