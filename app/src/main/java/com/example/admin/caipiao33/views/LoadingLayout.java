package com.example.admin.caipiao33.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;


/**
 * Caoxiangyu 2015.7.12 自定义loading View
 */
public class LoadingLayout extends RelativeLayout
{

    private View mLoadlayout;
    private CircularProgress loading_pb;
    private TextView loading_tv;
    private TextView reloading_tv;
    public OnReloadingListener onReloadingListener;
    private boolean isFirst = true;

    public LoadingLayout(Context context)
    {
        super(context);
        initView(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initView(context);
    }

    @SuppressLint("InflateParams")
    private void initView(Context context)
    {
        LayoutInflater mInflater = LayoutInflater.from(context);
        mLoadlayout = mInflater.inflate(R.layout.loading, null);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        addView(mLoadlayout, lp);
        loading_pb = (CircularProgress) mLoadlayout.findViewById(R.id.loading_pb);
        loading_tv = (TextView) mLoadlayout.findViewById(R.id.loading_tv);
        reloading_tv = (TextView) mLoadlayout.findViewById(R.id.reloading_tv);
        reloading_tv.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (null != onReloadingListener)
                {
                    onReloadingListener.onReload(v);
                }
            }
        });
    }

    /**
     * 進入頁面访问网络数据接口的时候执行该方法
     */
    public void setOnStartLoading(View visibleView)
    {
        loading_pb.setVisibility(View.VISIBLE);
        loading_tv.setVisibility(View.VISIBLE);
        reloading_tv.setVisibility(View.GONE);
        setVisibility(View.VISIBLE);
        if (visibleView != null)
            visibleView.setVisibility(View.GONE);
    }

    /**
     * 接口正确返回数据后调用此方法隐藏Loading
     */
    public void setOnStopLoading(Context context, View visibleView)
    {
        if (null == context)
        {
            return;
        }
        if (isFirst)
        {
            loading_pb.setVisibility(View.VISIBLE);
            loading_tv.setVisibility(View.VISIBLE);
            reloading_tv.setVisibility(View.GONE);
            isFirst = false;
            Animation mHiddenAction = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            Animation mShowAction = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            startAnimation(mHiddenAction);
            setVisibility(View.GONE);
            if (visibleView != null)
            {
                visibleView.startAnimation(mShowAction);
                visibleView.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 接口访问错误时调用此方法，刷新按钮的监听使用getReloadingTextView()获取到view后自行设置
     */
    public void setOnLoadingError(Context context, View visibleView)
    {
        loading_pb.setVisibility(View.GONE);
        loading_tv.setVisibility(View.GONE);
        reloading_tv.setVisibility(View.VISIBLE);
        Animation mHiddenAction = null;
        Animation mShowAction = null;
        try
        {
            mHiddenAction = AnimationUtils.loadAnimation(context, R.anim.fade_out);
            mShowAction = AnimationUtils.loadAnimation(context, R.anim.fade_in);
            startAnimation(mShowAction);
            setVisibility(View.VISIBLE);
            if (visibleView != null)
            {
                visibleView.startAnimation(mHiddenAction);
                visibleView.setVisibility(View.GONE);
            }
            isFirst = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setOnReloadingListener(@Nullable OnReloadingListener l)
    {
        if (!isClickable())
        {
            setClickable(true);
        }
        onReloadingListener = l;
    }

    /**
     * 当有的页面需要重新使用loadingLayout的时候把First变回true
     *
     * @param b
     */
    public void setFirtst(boolean b)
    {
        isFirst = b;
    }

    public interface OnReloadingListener
    {
        void onReload(View v);
    }

}
