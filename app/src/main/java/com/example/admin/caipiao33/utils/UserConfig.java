package com.example.admin.caipiao33.utils;

import android.content.Context;


/**
 * 用户数据获取对象
 */
public class UserConfig
{

    private static UserConfig mUserConfig;
    private String mToken;

    /**
     *
     */
    private UserConfig()
    {

    }

    public synchronized String getToken(Context context)
    {
        if (null == mToken)
        {
            try
            {
                mToken = decrypt(context, Constants.SHARE_TOKEN);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return mToken;
    }

    private String decrypt(Context context, String key)
    {
        String string = SharedPreUtils.getString(key, context);
        if (StringUtils.isEmpty(string))
        {
            return string;
        }
        return string;
        //        return P2PNative.getInstance().decrypt(string);
    }

    /**
     * @return
     */
    public synchronized static UserConfig getInstance()
    {
        if (null == mUserConfig)
        {
            mUserConfig = new UserConfig();
        }
        return mUserConfig;
    }

    /**
     * 清除内存中的用户数据,登出账户的时候使用该方法清除本地缓存数据
     */
    public synchronized void clear(Context context)
    {
        mToken = null;
        SharedPreUtils.putString(Constants.SHARE_TOKEN, "", context);
    }

    public synchronized void save(Context context, String token)
    {
        clear(context);
        SharedPreUtils.putString(Constants.SHARE_TOKEN, token, context);
    }
}
