package com.example.admin.caipiao33.views.banner;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class MyVerticalBanner extends LinearLayout
{
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private VerticalViewPager mAdvPager = null;
    /**
     * 滚动图片视图适配
     */
    private VerticalCycleAdapter mAdvAdapter;

    private boolean isStop;

    /**
     * @param context
     */
    public MyVerticalBanner(Context context)
    {
        super(context);
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public MyVerticalBanner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ad_vertical_cycle_view, this, true);
        mAdvPager = (VerticalViewPager) findViewById(R.id.adv_pager);
        mAdvPager.addOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setEnabled(false);
        mAdvPager.setFocusable(false);
        // 设置不能手动滑动
        mAdvPager.setCanScroll(false);
    }

    /**
     * 装填图片数据
     *
     * @param winList
     */
    public void setNewsData(List<HomePageBean.WinListBean> winList)
    {
        if (null == winList)
        {
            return;
        }
        if (null != mAdvAdapter)
        {
            mAdvAdapter.setmNewsList(winList);
            mAdvAdapter.notifyDataSetChanged();
            return;
        }
        mAdvAdapter = new VerticalCycleAdapter(mContext, winList);
        mAdvPager.setAdapter(mAdvAdapter);
        try
        {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller = new FixedSpeedScroller(mAdvPager.getContext(), new LinearInterpolator());
            mField.set(mAdvPager, mScroller);
            mScroller.setmDuration(800);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        startImageTimerTask();
    }

    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控件）
     */
    public void startImageCycle()
    {
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pushImageCycle()
    {
        stopImageTimerTask();
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask()
    {
        stopImageTimerTask();
        // 图片滚动
        mHandler.postDelayed(mImageTimerTask, 4000);
    }

    /**
     * 停止图片滚动任务
     */
    private void stopImageTimerTask()
    {
        isStop = true;
        mHandler.removeCallbacks(mImageTimerTask);
    }

    private Handler mHandler = new Handler();

    /**
     * 图片自动轮播Task
     */
    private Runnable mImageTimerTask = new Runnable()
    {
        @Override
        public void run()
        {
            mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
            if (!isStop)
            {  //if  isStop=true   //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                mHandler.postDelayed(mImageTimerTask, 4000);
            }
        }
    };

    public ViewPager getViewPager()
    {
        return mAdvPager;
    }

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrollStateChanged(int state)
        {
            if (state == ViewPager.SCROLL_STATE_IDLE)
                startImageTimerTask();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageSelected(int index)
        {
        }
    }

    private class VerticalCycleAdapter extends PagerAdapter
    {

        /**
         * 视图缓存列表
         */
        private ArrayList<LinearLayout> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private List<HomePageBean.WinListBean> mNewsList = new ArrayList<>();

        private Context mContext;

        public VerticalCycleAdapter(Context context, List<HomePageBean.WinListBean> winList)
        {
            this.mContext = context;
            this.mNewsList = winList;
            mImageViewCacheList = new ArrayList<>();
        }

        public List<HomePageBean.WinListBean> getmNewsList()
        {
            return mNewsList;
        }

        public void setmNewsList(List<HomePageBean.WinListBean> mNewsList)
        {
            this.mNewsList = mNewsList;
        }

        @Override
        public int getCount()
        {
            //			return mNewsList.size();
            if (mNewsList.size() == 1)
            {
                return 1;
            }
            else
            {
                return Integer.MAX_VALUE;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object obj)
        {
            return view == obj;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position)
        {
            LinearLayout layout;
            if (mImageViewCacheList.isEmpty())
            {
                layout = new LinearLayout(mContext);
                layout.setOrientation(LinearLayout.VERTICAL);
                //                ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
                //                if (null == layoutParams) {
                //                    layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                //                }
                for (int i = 0; i < 5; i++)
                {
                    View itemView = LayoutInflater.from(mContext)
                            .inflate(R.layout.item_vertical_view_pager, null);
                    layout.addView(itemView);
                }
            }
            else
            {
                layout = mImageViewCacheList.remove(0);
            }

            int childCount = layout.getChildCount();
            for (int i = 0; i < childCount; i++)
            {
                View itemView = layout.getChildAt(i);
                TextView tvLeft = ViewHolder.get(itemView, R.id.tv_left);
                TextView tvCenter = ViewHolder.get(itemView, R.id.tv_center);
                TextView tvRight = ViewHolder.get(itemView, R.id.tv_right);
                HomePageBean.WinListBean winListBean = mNewsList.get((position * 5 + i) % mNewsList.size());
                tvLeft.setText(winListBean.getUser());
                tvCenter.setText(mContext.getString(R.string.s_win_text, winListBean.getWinAmount()));
                tvRight.setText(mContext.getString(R.string.s_win_type, winListBean.getGname()));
            }
            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            LinearLayout view = (LinearLayout) object;
            container.removeView(view);
            mImageViewCacheList.add(view);
        }

    }

}
