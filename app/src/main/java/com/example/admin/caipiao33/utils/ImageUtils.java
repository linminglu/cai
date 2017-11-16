package com.example.admin.caipiao33.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * Created by CXY on 2017/11/16.
 */

public class ImageUtils
{
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    /**
     * 获取和保存当前屏幕的截图
     */
    public static boolean GetandSaveCurrentImage(Activity activity)
    {
        //构建Bitmap
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //获取屏幕
        View decorview = activity.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();
        //图片存储路径
        String SavePath = getSDCardPath() + "/Demo/ScreenImages";
        //保存Bitmap
        try
        {
            File path = new File(SavePath);
            //文件
            String filepath = SavePath + "/" + System.currentTimeMillis() + ".png";
            File file = new File(filepath);
            if (!path.exists())
            {
                path.mkdirs();
            }
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileOutputStream fos;
            fos = new FileOutputStream(file);
            if (null != fos)
            {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                ToastUtil.show("截屏文件已保存至相册");
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(filepath))));
                return true;
            }
            else
            {
                ToastUtil.show("截屏文件保存失败");
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     */
    private static String getSDCardPath()
    {
        File sdcardDir = null;
        //判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist)
        {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    private static boolean hasExternalStoragePermission(Context context)
    {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * Returns application cache directory. Cache directory will be created on SD card
     * <i>("/Android/data/[app_package_name]/cache")</i> if card is mounted and app has appropriate permission. Else -
     * Android defines cache directory on device's file system.
     *
     * @param context Application context
     * @return Cache {@link File directory}
     */
    public static File getCacheDirectory(Context context, String file)
    {
        File appCacheDir = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context))
        {
            appCacheDir = new File(file);
        }
        if (appCacheDir == null)
        {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null)
        {
            Log.w("ImageUtils", "Can't define system cache directory! The app should be re-installed.");
        }
        return appCacheDir;
    }
}
