package com.example.admin.caipiao33.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * yyyy-MM-dd HH:mm:ss
 */
public class DateUtils
{

    /**
     * 判断传进时间 是否是当天
     *
     * @param timeStamp 秒
     * @return
     */
    public static boolean lastSignTimeIsToday(long timeStamp)
    {

        return getCurrentDay() == getCurrentDayTime(timeStamp);
    }

    /**
     * 判断传进时间 是否是昨天
     *
     * @param timeStamp 秒
     * @return
     */
    public static boolean lastSignedIsYesterday(long timeStamp)
    {

        return getCurrentDay() == getCurrentDayTime(timeStamp + 3600 * 24);
    }

    /**
     * 获取昨天0点的时间戳  秒
     *
     * @return
     */
    public static long getYesterdayDayTime()
    {
        return getCurrentDayTime(getCurrentTime() - 3600 * 24);
    }

    /**
     * 获取0点的时间戳 秒
     *
     * @param
     * @return
     */
    public static long getCurrentDay()
    {
        return getCurrentDayMillTime(getCurrentTime()) / 1000;
    }

    public static long getCurrentDay(long currentTime)
    {
        return getCurrentDayMillTime(currentTime) / 1000;
    }

    /**
     * 获取0点的时间戳
     *
     * @param timeStamp
     * @return
     */
    public static long getCurrentDayTime(long timeStamp)
    {
        return getCurrentDayMillTime(timeStamp) / 1000;
    }

    /**
     * 获取0点的时间戳
     *
     * @param timeStamp
     * @return
     */
    public static long getCurrentDayMillTime(long timeStamp)
    {
        Calendar c = Calendar.getInstance(Locale.getDefault());
        c.setTimeInMillis(timeStamp * 1000);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    /**
     * 获取当前点的时间戳
     *
     * @param
     * @return
     */
    public static long getCurrentTime()
    {
        return Calendar.getInstance().getTimeInMillis() / 1000;
    }

    /**
     * 获取当前时分格式的string
     *
     * @param
     * @return
     */
    public static String getCurrentHMTimeStr()
    {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String d = format.format(Calendar.getInstance().getTimeInMillis());
        return d;
    }

    /**
     * 获取年月日格式的string
     *
     * @param
     * @return
     */
    public static String getYMDTimeStr(long date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(new Date(date));
        return d;
    }

    /**
     * 获取年月日格式的string
     *
     * @param
     * @return
     */
    public static String getTimeStr(long date, String format)
    {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        String d = sf.format(new Date(date));
        return d;
    }

    /**
     * 时间格式获取---》微妙
     */
    public static long getMillionTimeFromFormat(String dateStr, String format)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(dateStr);
            return d.getTime();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getHMS(long totalSeconds) {
        int hh = (int) (totalSeconds / 60 / 60 % 60);
        int mm = (int) (totalSeconds / 60 % 60);
        int ss = (int) (totalSeconds % 60);
        return (hh < 10 ? "0" + hh : hh) + ":" +
                (mm < 10 ? "0" + mm : mm) + ":" +
                (ss < 10 ? "0" + ss : ss);
    }
}
