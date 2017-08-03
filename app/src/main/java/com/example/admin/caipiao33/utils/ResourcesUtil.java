package com.example.admin.caipiao33.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ResourcesUtil
{

    public static int getDip(Context context, int resId)
    {
        return Math.round(context.getResources().getDimension(resId));
    }

    public static int getDip(Resources res, int resId)
    {
        return Math.round(res.getDimension(resId));
    }

    public static int dip2Px(Resources res, int dip)
    {
        DisplayMetrics dm = res.getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm));
    }

    public static int px2dip(Context context, int px)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, px, context.getResources()
                .getDisplayMetrics());
    }

    public static int px2sp(Context context, float pxValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }

    public static final View loadLayout(Context context, int layoutId)
    {
        return loadLayout(context, layoutId, null);
    }

    public static final View loadLayout(Context context, int layoutId, ViewGroup gp)
    {
        return loadLayout(context, layoutId, gp, gp != null);
    }

    public static final View loadLayout(Context context, int layoutId, ViewGroup gp, boolean attach)
    {
        return LayoutInflater.from(context).inflate(layoutId, gp, attach);
    }
}
