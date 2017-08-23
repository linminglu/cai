package com.example.admin.caipiao33.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.example.admin.caipiao33.BuildConfig;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.utils.Global;
import com.socks.library.KLog;


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
