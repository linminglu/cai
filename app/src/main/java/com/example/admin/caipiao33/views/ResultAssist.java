package com.example.admin.caipiao33.views;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.adapter.MyBaseBuyAdapter;
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
                // 适配六 合 的情况
                if (mBuyRoomBean.getNum().equals(MyBaseBuyAdapter.TYPE_SIX))
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
