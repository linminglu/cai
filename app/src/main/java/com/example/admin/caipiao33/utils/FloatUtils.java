package com.example.admin.caipiao33.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Float 工具类封装
 * caoxiangyu 2016/7/11
 */
public class FloatUtils
{

    private static DecimalFormat FORMAT = new DecimalFormat("0.00");

    public static void main(String[] agr)
    {
        System.out.println(decimalNum(2.55666666F, 3));
    }

    /**
     * 处理小数点位数
     *
     * @param d
     * @param num 小数点后num位
     * @return
     */
    public static float decimalNum(float d, int num)
    {
        BigDecimal bg = new BigDecimal(d);
        float f1 = bg.setScale(num, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    /**
     * 处理小数点位数
     * <p>
     * 默认为小数点后两位
     *
     * @param d
     * @return
     */
    public static float decimalNum(float d)
    {
        BigDecimal bg = new BigDecimal(d);
        float f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

        return f1;
    }

    /**
     * 将float类型的数据格式化成字符串表示,保留两位小数
     *
     * @param d
     * @return
     */
    public static String format(float d)
    {
        return FORMAT.format(d);
    }

    /**
     * 将object类型对象转换为float类型
     *
     * @param obj     要转换成float类型的对象
     * @param decimal 保留的小数位数
     * @return
     */
    public static float toFloat(Object obj, int decimal)
    {
        if (decimal < 0)
        {
            throw new IllegalArgumentException("illegal decimal value [" + decimal + "]");
        }
        String s = "0";
        if (obj != null)
        {
            s = obj.toString().trim();
        }
        if (s.length() == 0)
        {
            s = "0";
        }
        BigDecimal bg = new BigDecimal(s);
        return bg.setScale(decimal, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 将object类型对象转换为float类型,保留两位小数
     *
     * @param obj 要转换成float类型的对象
     * @return
     */
    public static float toFloat(Object obj)
    {
        return toFloat(obj, 2);
    }

    /**
     * 精确的乘法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static float mul(Float d1, Float d2)
    {
        BigDecimal bgd1 = new BigDecimal(Float.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Float.toString(d2 == null ? 0 : d2));
        return bgd1.multiply(bgd2).floatValue();
    }

    /**
     * 精确的减法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static float sub(Float d1, Float d2)
    {
        BigDecimal bgd1 = new BigDecimal(Float.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Float.toString(d2 == null ? 0 : d2));
        return bgd1.subtract(bgd2).floatValue();
    }

    /**
     * 精确的加法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static float add(Float d1, Float d2)
    {
        BigDecimal bgd1 = new BigDecimal(Float.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Float.toString(d2 == null ? 0 : d2));
        return bgd1.add(bgd2).floatValue();
    }

    /**
     * 精确的除法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static float div(Float d1, Float d2)
    {
        BigDecimal bgd1 = new BigDecimal(Float.toString(d1 == null ? 0 : d1));
        BigDecimal bgd2 = new BigDecimal(Float.toString(d2 == null ? 1 : d2));
        return bgd1.divide(bgd2, 6, BigDecimal.ROUND_HALF_DOWN).floatValue();
    }
}
