package com.example.admin.caipiao33.views.loadmore;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.utils.Tools;


/**
 * Created by lsd on 2016/5/24.
 */
public class LoadingMoreFooter extends LinearLayout
{
    private Context context;
    private LinearLayout loading_view_layout;
    private LinearLayout end_layout;
    private IProgressView iProgressView;

    public LoadingMoreFooter(Context context)
    {
        super(context);
        this.context = context;
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context)
    {
        setGravity(Gravity.CENTER);
        //        setLayoutParams(new ViewGroup.LayoutParams(
        //                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
        //        LayoutInflater layoutInflater = LayoutInflater.from(context);
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, Tools
                .dp2px(context, 50)));
        loading_view_layout = new LinearLayout(context);
        loading_view_layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        loading_view_layout.setGravity(Gravity.CENTER);
        loading_view_layout.setOrientation(LinearLayout.VERTICAL);
        frameLayout.addView(loading_view_layout);

        end_layout = new LinearLayout(context);
        end_layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        end_layout.setGravity(Gravity.CENTER);
        end_layout.setOrientation(LinearLayout.VERTICAL);
        end_layout.setBackgroundColor(Color.TRANSPARENT);
        end_layout.setVisibility(View.GONE);
        frameLayout.addView(end_layout);

        addFootLoadingView(new PointProgressView(context));

        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(R.string.s_load_more_end);
        addFootEndView(textView);

        addView(frameLayout);
    }

    //设置底部加载中效果
    public void addFootLoadingView(IProgressView iProgressView)
    {
        this.iProgressView = iProgressView;
        loading_view_layout.removeAllViews();
        loading_view_layout.addView(iProgressView.getView());
    }

    //设置底部到底了布局
    public void addFootEndView(View view)
    {
        end_layout.removeAllViews();
        end_layout.addView(view);
    }

    //设置已经没有更多数据
    public void setEnd()
    {
        setVisibility(VISIBLE);
        loading_view_layout.setVisibility(GONE);
        end_layout.setVisibility(VISIBLE);
        if (null != iProgressView)
        {
            iProgressView.endAnim();
        }
    }

    public void setVisible()
    {
        setVisibility(VISIBLE);
        loading_view_layout.setVisibility(VISIBLE);
        end_layout.setVisibility(GONE);
        if (null != iProgressView)
        {
            iProgressView.startAnim();
        }
    }

    public void setGone()
    {
        setVisibility(GONE);
        if (null != iProgressView)
        {
            iProgressView.endAnim();
        }
    }
}
