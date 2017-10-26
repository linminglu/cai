package com.example.admin.caipiao33.views;

import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.adapter.MyBaseBuyAdapter;
import com.example.admin.caipiao33.fragment.adapter.TypeQuick3Adapter;
import com.example.admin.caipiao33.fragment.adapter.TypeSixAdapter;
import com.example.admin.caipiao33.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 开奖结果辅助类，用于控制显示结果的各种情况
 */

public class ResultAssist
{
    private BuyRoomBean mBuyRoomBean;
    private LayoutInflater mInflater;
    private LinearLayout layoutResult;
    private String mLastOpen;
    private TextView tvWait;
    private List<TextView> recyclerList = new ArrayList<>();

    public ResultAssist(LayoutInflater layoutInflater, LinearLayout layoutResult, BuyRoomBean bean, String lastOpen)
    {
        this.mInflater = layoutInflater;
        this.layoutResult = layoutResult;
        this.mBuyRoomBean = bean;
        updateData(lastOpen);
    }

    public void updateData(String lastOpen)
    {
        if (StringUtils.isEmpty(lastOpen))
        {
            if (StringUtils.isEmpty(mLastOpen))
            {
                // 首次就是为空的情况处理
                if (layoutResult.getChildCount() == 0)
                {
                    if (null == tvWait)
                    {
                        tvWait = (TextView) mInflater.inflate(R.layout.item_wait_text, layoutResult, false);
                    }
                    layoutResult.addView(tvWait);
                }
                return;
            }
            layoutResult.removeAllViews();
            if (null == tvWait)
            {
                tvWait = (TextView) mInflater.inflate(R.layout.item_wait_text, layoutResult, false);
            }
            layoutResult.addView(tvWait);
        }
        else
        {
            if (lastOpen.equals(mLastOpen))
            {
                return;
            }
            layoutResult.removeAllViews();
            String[] split = lastOpen.split(",");
            for (int i = 0; i < split.length; i++)
            {
                TextView tv;
                if (i < recyclerList.size())
                {
                    tv = recyclerList.get(i);
                }
                else
                {
                    tv = (TextView) mInflater.inflate(R.layout.item_result_text, layoutResult, false);
                    tv.setGravity(Gravity.CENTER);
                    recyclerList.add(tv);
                }
                String text = split[i];
                tv.setText(text);
                String num = mBuyRoomBean.getNum();
                // 适配六 合 的情况
                if (num.equals(MyBaseBuyAdapter.TYPE_SIX))
                {
                    if (TypeSixAdapter.NUMBER_RED.contains(text))
                    {
                        tv.setBackgroundResource(R.drawable.shape_circle_red);
                    }
                    else if (TypeSixAdapter.NUMBER_GREEN.contains(text))
                    {
                        tv.setBackgroundResource(R.drawable.shape_circle_green);
                    }
                    else if (TypeSixAdapter.NUMBER_BLUE.contains(text))
                    {
                        tv.setBackgroundResource(R.drawable.shape_circle_blue);
                    }
                    else
                    {
                        tv.setBackgroundDrawable(null);
                    }
                } else if (num.equals("52") || num.equals("9")) {
                    // 适配pk10  52 - 三分PK10; 9 - 北京赛车(PK10)
                    switch (text) {
                        case "01":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_1);
                            break;
                        case "02":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_2);
                            break;
                        case "03":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_3);
                            break;
                        case "04":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_4);
                            break;
                        case "05":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_5);
                            break;
                        case "06":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_6);
                            break;
                        case "07":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_7);
                            break;
                        case "08":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_8);
                            break;
                        case "09":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_9);
                            break;
                        case "10":
                            tv.setBackgroundResource(R.drawable.shape_rectangle_10);
                            break;
                    }
                } else if (TypeQuick3Adapter.QUICK3List.contains(num)) {
                    tv.setBackgroundDrawable(null);
                    SpannableString msp = new SpannableString(text);
                    switch (text)
                    {
                        case "1":
                            Drawable drawable1 = mInflater.getContext()
                                    .getResources()
                                    .getDrawable(R.mipmap.touzi_01);
                            drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth()*4/5, drawable1.getIntrinsicHeight()*4/5);
                            msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case "2":
                            Drawable drawable2 = mInflater.getContext()
                                    .getResources()
                                    .getDrawable(R.mipmap.touzi_02);
                            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth()*4/5, drawable2.getIntrinsicHeight()*4/5);
                            msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case "3":
                            Drawable drawable3 = mInflater.getContext()
                                    .getResources()
                                    .getDrawable(R.mipmap.touzi_03);
                            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth()*4/5, drawable3.getIntrinsicHeight()*4/5);
                            msp.setSpan(new ImageSpan(drawable3), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case "4":
                            Drawable drawable4 = mInflater.getContext()
                                    .getResources()
                                    .getDrawable(R.mipmap.touzi_04);
                            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth()*4/5, drawable4.getIntrinsicHeight()*4/5);
                            msp.setSpan(new ImageSpan(drawable4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case "5":
                            Drawable drawable5 = mInflater.getContext()
                                    .getResources()
                                    .getDrawable(R.mipmap.touzi_05);
                            drawable5.setBounds(0, 0, drawable5.getIntrinsicWidth()*4/5, drawable5.getIntrinsicHeight()*4/5);
                            msp.setSpan(new ImageSpan(drawable5), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                        case "6":
                            Drawable drawable6 = mInflater.getContext()
                                    .getResources()
                                    .getDrawable(R.mipmap.touzi_05);
                            drawable6.setBounds(0, 0, drawable6.getIntrinsicWidth()*4/5, drawable6.getIntrinsicHeight()*4/5);
                            msp.setSpan(new ImageSpan(drawable6), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            break;
                    }
                    tv.setText(msp);
                }
                layoutResult.addView(tv);
            }
        }
        mLastOpen = lastOpen;
    }

    public void clear()
    {
        if (null != recyclerList)
        {
            recyclerList.clear();
            recyclerList = null;
        }
        if (null != mInflater)
        {
            mInflater = null;
        }
        if (null != tvWait)
        {
            tvWait = null;
        }
    }
}
