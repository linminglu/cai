package com.example.admin.caipiao33.utils;

/**
 * 服务器时间
 *
 * @author Administrator
 */
public class ServiceTime
{
    /*
     * 偏差 = 服务器时间 - 本地时间
	 */

    private static ServiceTime mServiceTime = null;

    private long offsetTime = 0;

    private ServiceTime()
    {

    }

    public static ServiceTime getInstance()
    {
        if (null == mServiceTime)
        {
            mServiceTime = new ServiceTime();
        }
        return mServiceTime;
    }

    /**
     * 校正时间
     *
     * @param serviceTime
     */
    public void proofTime(long serviceTime)
    {
        long localTime = System.currentTimeMillis();
        offsetTime = 800 + serviceTime - localTime;
    }

    /**
     * 取校正后的时间
     *
     * @return
     */
    public long getCurrentTimeMillis()
    {
        return System.currentTimeMillis() + offsetTime;
    }
}
