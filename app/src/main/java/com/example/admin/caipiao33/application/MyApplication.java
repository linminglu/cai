package com.example.admin.caipiao33.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.admin.caipiao33.BuildConfig;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Global;
import com.socks.library.KLog;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;


public class MyApplication extends Application
{
    private static MyApplication app;
    private BaseUrlBean mBaseUrlBean;

    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        app = this;
        Global.setApplicationContext(this);
        // 日志工具初始化
        KLog.init(BuildConfig.LOG_DEBUG);
        KLog.e("MyApplication onCreate");
        HttpUtil.reConfirmUrl(this, new MyResponseListener<BaseUrlBean>()
        {
            @Override
            public void onSuccess(BaseUrlBean result)
            {
                if (null == result) {
                    return;
                }
                mBaseUrlBean = result;
                HttpUtil.changeBaseUrl(result.getUrl());
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {

            }
        });

        PushAgent mPushAgent = PushAgent.getInstance(this);
//        mPushAgent.setResourcePackageName("com.example.admin.caipiao33");
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    public synchronized static MyApplication getInstance()
    {
        return app;
    }

    public void setBaseUrlBean(BaseUrlBean result) {
        this.mBaseUrlBean = result;
    }

    public BaseUrlBean getBaseUrlBean() {
        return mBaseUrlBean;
    }
}
