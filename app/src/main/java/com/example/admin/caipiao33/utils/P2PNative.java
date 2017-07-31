package com.example.admin.caipiao33.utils;

/**
 * Created by lsd on 2015/12/16.
 */
public class P2PNative
{
    static
    {
        System.loadLibrary("p2pnative");
    }

    private volatile static P2PNative mInstance;

    private P2PNative()
    {
    }

    public native String encrypt(String input);

    public native String decrypt(String input);

    public static P2PNative getInstance()
    {
        if (null == mInstance)
        {
            synchronized (P2PNative.class)
            {
                if (null == mInstance)
                {
                    mInstance = new P2PNative();
                }
            }
        }
        return mInstance;
    }
}
