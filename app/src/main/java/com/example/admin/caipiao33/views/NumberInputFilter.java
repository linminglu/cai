package com.example.admin.caipiao33.views;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 限制输入整数的金额
 * 不能以0开头
 */
public class NumberInputFilter implements InputFilter
{
    private static final String ZERO = "0";
    Pattern mPattern;

    public NumberInputFilter() {
        mPattern = Pattern.compile("[0-9]*");
    }

    /**
     * @param source 新输入的字符串
     * @param start 新输入的字符串起始下标，一般为0c
     * @param end 新输入的字符串终点下标，一般为source长度-1
     * @param dest 输入之前文本框内容
     * @param dstart 原内容起始坐标，一般为0
     * @param dend 原内容终点坐标，一般为dest长度-1
     * @return
     */
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend)
    {
        String sourceStr = source.toString();
        String destStr = dest.toString();

        if (TextUtils.isEmpty(sourceStr)) {
            return "";
        }

        Matcher matcher = mPattern.matcher(sourceStr);
        if (!matcher.matches()) {
            return "";
        }
        if (sourceStr.startsWith(ZERO) && TextUtils.isEmpty(destStr)) {
            return "";
        }

        return dest.subSequence(dstart, dend) + sourceStr;
    }
}
