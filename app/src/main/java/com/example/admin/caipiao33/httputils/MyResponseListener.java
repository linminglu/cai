package com.example.admin.caipiao33.httputils;


/**
 * Created by lsd on 2016/1/8.
 */
public abstract class MyResponseListener<T>
{
    public abstract void onSuccess(T result);

    public abstract void onFailed(int code, String msg);

    public abstract void onFinish();
}
