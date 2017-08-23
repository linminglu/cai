package com.example.admin.caipiao33.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

/**
 * App相关辅助类
 */
public class AppUtils
{

    private AppUtils()
    {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取应用程序名称
     */
    public static PackageInfo getPackageInfo(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            // int labelRes = packageInfo.applicationInfo.labelRes;
            // return context.getResources().getString(labelRes);
            return packageInfo;
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查找该包名的应用是否存在
     *
     * @param context
     * @return
     */
    public static boolean isAppExist(Context context, String pkg)
    {
        try
        {
            context.getPackageManager()
                    .getApplicationInfo(pkg, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }
        catch (NameNotFoundException e)
        {
            return false;
        }
    }

    /**
     * 卸载应用
     *
     * @param context
     * @param pkg
     */
    public static void uninstall(Context context, String pkg)
    {
        if (pkg == null)
        {
            return;
        }
        Uri uri = Uri.fromParts("package", pkg, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uri);
        context.startActivity(intent);
    }

    /**
     * 返回当前程序版本名
     *
     * @return 2.0.x
     */
    public static int getAppVersionCode(Context context)
    {
        int versionCode = 0;
        try
        {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = pi.versionCode;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return versionCode;
    }

}
