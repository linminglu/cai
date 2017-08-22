package com.example.admin.caipiao33.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.SwipeRefreshLayout;

import com.socks.library.KLog;

/**
 * Created by lsd on 2016/5/24.
 */
public class Tools
{
    public static void setSwipeRefreshColor(SwipeRefreshLayout swipeLayout)
    {
        //设置刷新时动画的颜色，可以设置4个
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
    }

    /**
     * <功能详细描述>判断网络是否可用<br>
     *
     * @param context
     * @return<br>
     */
    public static boolean isNetworkAvailable(Context context)
    {
        if (context == null)
        {
            return false;
        }
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null)
        {
            KLog.w("Utils", "couldn't get connectivity manager");
        }
        else
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 着色器
     *
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors)
    {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public static int dp2px(Context context, int dpValue)
    {
        return (int) context.getResources().getDisplayMetrics().density * dpValue;
    }
}
