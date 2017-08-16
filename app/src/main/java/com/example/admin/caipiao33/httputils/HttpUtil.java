package com.example.admin.caipiao33.httputils;


import android.app.Activity;
import android.content.Intent;

import com.example.admin.caipiao33.encryption.CreateCode;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.UserConfig;
import com.google.gson.Gson;
import com.socks.library.KLog;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HttpUtil
{
    private static final int DEFAULT_TIMEOUT = 30;
    private static Retrofit retrofit;
    private static Retrofit retrofitBase;
    private static SSLContext sslContext;
    public static String mNewUrl;

    static
    {
        //手动创建一个OkHttpClient并设置超时时间
        overlockCard();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.sslSocketFactory(sslContext.getSocketFactory());

        retrofitBase = new Retrofit.Builder().client(builder.build())
                .baseUrl(Constants.URL)
                .addConverterFactory(MyGsonConverterFactory.create())
                //                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        retrofit = retrofitBase;
    }

    public static void changeBaseUrl(String url)
    {
        overlockCard();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.sslSocketFactory(sslContext.getSocketFactory());
        mNewUrl = url;
        retrofit = new Retrofit.Builder().client(builder.build())
                .baseUrl(url)
                .addConverterFactory(MyGsonConverterFactory.create())
                //                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * 忽略所有https证书
     */
    private static void overlockCard()
    {
        final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager()
        {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
            {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException
            {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers()
            {
                X509Certificate[] x509Certificates = new X509Certificate[0];
                return x509Certificates;
            }
        }};
        try
        {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        }
        catch (Exception e)
        {
            KLog.e("ssl出现异常");
        }

    }

    public static void setCard(InputStream certificate)
    {
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            String certificateAlias = Integer.toString(0);
            keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
            sslContext = SSLContext.getInstance("TLS");
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory
                    .getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
        }
        catch (CertificateException e)
        {
            e.printStackTrace();
        }
        catch (KeyStoreException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (KeyManagementException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 参数Type的说明：
     * 由原来的Class改变成Type
     * Class这个对象是实现Type这个接口
     * 所以可以替换原来的Class参数
     * 正常具体对象直接传入对象的Class即可
     * 当要解析的结果是一个不带Key的JSONArray时可以使用Type来解析
     * 例子：new TypeToken<ArrayList<BeanAddress>>(){}.getType()
     * 显示加载中的Dialog
     *
     * @deprecated Use {@link #post(TreeMap, Type, Activity, MyResponseListener, Intent)}
     */
    @Deprecated
    public static void post(TreeMap<String, String> map, final Type clazz, final Activity mActivity, final MyResponseListener listener) // 带参数，获取json对象或者数组
    {
        post(map, clazz, mActivity, listener, null);
    }

    public static void post(TreeMap<String, String> map, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent) // 带参数，获取json对象或者数组
    {
        post(map, null, clazz, mActivity, listener, intent);
    }

    public static void postBase(Call<String> call, TreeMap<String, String> map, TreeMap<String, File> fileMap, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent) // 带参数，获取json对象或者数组
    {
        call.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                if (null != listener)
                {
                    listener.onFinish();
                }
                try
                {
                    int responseCode = response.code();
                    if (responseCode != 200)
                    {
                        String string = "网络异常！";
                        KLog.d("errorNo: " + responseCode + " strMsg: " + response.errorBody()
                                .string());
                        listener.onFailed(responseCode, string);
                        return;
                    }
                    String result = CreateCode.parseContent(response.body());
                    KLog.d(result);
                    JSONObject object = new JSONObject(result);
                    int code = object.getInt("status");
                    if (code == Constants.SUCCESSCODE)
                    {
                        String token = object.optString("token");
                        String data = object.optString("data");
                        if (!StringUtils.isEmpty2(token))
                        {
                            UserConfig.getInstance().save(mActivity, token);
                        }
                        //                                                String result = CreateCode.parseContent(object.optString("result"));
                        KLog.d(data);
                        if (null == clazz)
                        {
                            listener.onSuccess(null);
                            return;
                        }
                        else if (clazz == String.class)
                        {
                            listener.onSuccess(data);
                            return;
                        }
                        Object o = new Gson().fromJson(data, clazz);
                        listener.onSuccess(o);
                    }
                    else
                    {
                        KLog.d(object.toString());
                        listener.onFailed(code, object.getString("description"));
                        return;
                    }
                }
                catch (Exception e)
                {
                    KLog.d(response.body());
                    listener.onFailed(Constants.UNKNOWCODE, Constants.JSONERROR);
                    e.printStackTrace();
                    return;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                if (null != listener)
                {
                    listener.onFinish();
                }
                int errorNo = Constants.NETWORK_ERROR;
                String strMsg = t.getMessage();

                if (t instanceof UnknownHostException || StringUtils.isEmpty(strMsg))
                {
                    strMsg = "网络异常！";
                    errorNo = Constants.NETWORK_ERROR;
                }
                KLog.d("errorNo: " + errorNo + " strMsg: " + strMsg);
                listener.onFailed(errorNo, strMsg);
            }
        });
    }

    public static void post(TreeMap<String, String> map, TreeMap<String, File> fileMap, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent) // 带参数，获取json对象或者数组
    {
        Call<String> call;
        TreeMap<String, String> params;
        CommonPostService commonPostService = retrofit.create(CommonPostService.class);
        if (map != null)
        {
            call = commonPostService.getLogin1(map.get("data"));
        }
        else
        {
            call = commonPostService.getHomePage();
        }

        KLog.d(map);

        postBase(call, map, fileMap, clazz, mActivity, listener, intent);
    }

    public static void requestFirst(String name, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent) // 带参数，获取json对象或者数组
    {
        requestFirst(name, null, clazz, mActivity, listener, intent);
    }

    public static void requestFirst(String name, HashMap<String, String> map, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent)
    {
        Call<String> call;
        FirstService firstService = retrofit.create(FirstService.class);
        HashMap<String, String> params = CreateCode.getParams(map);
        call = firstService.getFirstRepos(name, params);
        KLog.e("requestFirst: " + call.request().url().toString());
        postBase(call, null, null, clazz, mActivity, listener, intent);
    }

    public static void requestSecond(String name, String second, HashMap<String, String> map, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent)
    {
        Call<String> call;
        FirstService firstService = retrofit.create(FirstService.class);
        HashMap<String, String> params = CreateCode.getParams(map);
        call = firstService.getSecondRepos(name, second, params);
        KLog.e("requestFirst: " + call.request().url().toString());
        postBase(call, null, null, clazz, mActivity, listener, intent);
    }

    public static void requestLogin(HashMap<String, String> map, final Type clazz, final Activity mActivity, final MyResponseListener listener, final Intent intent)
    {
        Call<String> call;
        LoginService loginService = retrofit.create(LoginService.class);
        HashMap<String, String> params = CreateCode.getParams(map);
        call = loginService.getLogin(params);
        KLog.e("requestFirst: " + call.request().url().toString());
        postBase(call, null, null, clazz, mActivity, listener, intent);
    }

}