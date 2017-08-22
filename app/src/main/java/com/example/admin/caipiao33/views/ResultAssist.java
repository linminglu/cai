package com.example.admin.caipiao33.views;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 开奖结果辅助类，用于控制显示结果的各种情况
 */

public class ResultAssist
{
    private LayoutInflater mInflater;
    private LinearLayout layoutResult;
    private String mLastOpen;
    private TextView tvWait;
    private List<TextView> recyclerList = new ArrayList<>();

    public ResultAssist(LayoutInflater layoutInflater, LinearLayout layoutResult, BuyRoomBean bean, String lastOpen) {
        this.mInflater = layoutInflater;
        this.layoutResult = layoutResult;
        if (StringUtils.isEmpty(lastOpen)) {
            tvWait = (TextView) mInflater.inflate(R.layout.item_wait_text, layoutResult, false);
            layoutResult.addView(tvWait);
        } else {
            String[] split = lastOpen.split(",");
            for (int i = 0; i < split.length; i++) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_result_text, layoutResult, false);
                tv.setGravity(Gravity.CENTER);
                tv.setText(split[i]);
                recyclerList.add(tv);
                layoutResult.addView(tv);
            }
        }
        this.mLastOpen = lastOpen;
    }

    public void updateData(String lastOpen)
    {

        if (StringUtils.isEmpty(lastOpen)) {
            if (StringUtils.isEmpty(mLastOpen)) {
                return;
            }
            layoutResult.removeAllViews();
            if (null == tvWait) {
                tvWait = (TextView) mInflater.inflate(R.layout.item_wait_text, layoutResult, false);
            }
            layoutResult.addView(tvWait);
        } else {
            if (lastOpen.equals(mLastOpen)) {
                return;
            }
            layoutResult.removeAllViews();
            String[] split = lastOpen.split(",");
            for (int i = 0; i < split.length; i++) {
                if (i < recyclerList.size()) {
                    TextView textView = recyclerList.get(i);
                    textView.setText(split[i]);
                    layoutResult.addView(textView);
                } else {
                    TextView tv = (TextView) mInflater.inflate(R.layout.item_result_text, layoutResult, false);
                    tv.setGravity(Gravity.CENTER);
                    tv.setText(split[i]);
                    recyclerList.add(tv);
                    layoutResult.addView(tv);
                }
            }
        }
        mLastOpen = lastOpen;
    }

    public void clear() {
        if (null != recyclerList) {
            recyclerList.clear();
            recyclerList = null;
        }
        if (null != mInflater) {
            mInflater = null;
        }
        if (null != tvWait) {
            tvWait = null;
        }
    }
}
