package com.example.admin.caipiao33.views;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by CXY on 2017/11/21.
 */

public class CusRefreshLayout extends SwipeRefreshLayout
{
    private Float mStartX;
    private Float mStartY;
    private boolean mIsVpDragger;//是否左右拖动中;
    private boolean mlvistop;//容器中的listview是否在顶部;

    private int mTouchSlop;//滑动距离监听;

    public CusRefreshLayout(Context context)
    {
        super(context, null);
    }

    public CusRefreshLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean isMlvistop()
    {
        return mlvistop;
    }

    public void setMlvistop(boolean mlvistop)
    {
        this.mlvistop = mlvistop;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {

        final int action = MotionEventCompat.getActionMasked(ev);


        switch (action)
        {

            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                mStartY = ev.getY();
                mIsVpDragger = false;

                break;

            case MotionEvent.ACTION_MOVE:

                // 如果viewpager正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsVpDragger || mlvistop)
                {
                    return false;
                }
                //获取当前手的位置

                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - mStartX);
                float distanceY = Math.abs(endY - mStartY);
                if (distanceX > mTouchSlop && distanceX > distanceY)
                {
                    mIsVpDragger = true;
                    return false;
                }

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsVpDragger = false;
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return super.onTouchEvent(ev);
    }
}
