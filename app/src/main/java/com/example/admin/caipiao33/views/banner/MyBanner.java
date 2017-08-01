package com.example.admin.caipiao33.views.banner;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class MyBanner extends RelativeLayout
{
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 图片轮播视图
     */
    private ViewPager mAdvPager = null;
    /**
     * 滚动图片视图适配
     */
    private ImageCycleAdapter mAdvAdapter;
    /**
     * 图片轮播指示器控件
     */
    private LinearLayout mGroup;

    /**
     * 滚动图片指示视图列表
     */
    private TextView[] mPointViews = null;

    /**
     * 图片滚动当前图片下标
     */
    private int mImageIndex = 0;
    /**
     * 手机密度
     */
    private float mScale;
    private boolean isStop;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private int mSpaceTime = 4000;

    /**
     * @param context
     */
    public MyBanner(Context context)
    {
        super(context);
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public MyBanner(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        mContext = context;
        mScale = context.getResources().getDisplayMetrics().density;

        mAdvPager = new ViewPager(context);
        mAdvPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mAdvPager.addOnPageChangeListener(new GuidePageChangeListener());
        mAdvPager.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_UP:
                        // 开始图片滚动
                        if (mSwipeRefreshLayout != null)
                            mSwipeRefreshLayout.setEnabled(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        stopImageTimerTask();
                        if (mSwipeRefreshLayout != null)
                            mSwipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        stopImageTimerTask();
                        if (mSwipeRefreshLayout != null)
                            mSwipeRefreshLayout.setEnabled(true);
                        break;
                    default:
                        // 停止图片滚动
                        stopImageTimerTask();
                        break;
                }
                return false;
            }
        });
        addView(mAdvPager);

        // 滚动图片右下指示器视
        mGroup = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mGroup.setGravity(Gravity.CENTER);
        mGroup.setOrientation(LinearLayout.HORIZONTAL);
        int padding = (int) context.getResources().getDisplayMetrics().density * 5;
        mGroup.setPadding(0, 0, 0, padding);
        mGroup.setLayoutParams(params);
        addView(mGroup);
    }


    /**
     * 装填图片数据
     *
     * @param :
     * @param imageCycleViewListener
     */
    public void setImageResources(List<String> imageUrlList, ImageCycleViewListener imageCycleViewListener)
    {
        if (imageUrlList != null && imageUrlList.size() > 0)
        {
            this.setVisibility(View.VISIBLE);
        }
        else
        {
            this.setVisibility(View.GONE);
            return;
        }

        // 清除
        mGroup.removeAllViews();
        // 图片广告数量
        final int imageCount = imageUrlList.size();
        mPointViews = new TextView[imageCount];
        for (int i = 0; i < imageCount; i++)
        {
            TextView point = new TextView(mContext);
            point.setText("\u25CF");// 圆点符号
            point.setTextSize(mScale * 4 + 0.5f);
            int imagePadding = (int) (mScale * 1 + 0.5f);// XP与DP转换，适应应不同分辨率
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            point.setLayoutParams(params);
            point.setPadding(imagePadding, imagePadding, imagePadding, imagePadding);
            //            point.setImageResource(R.drawable.default_load);

            if (i == 0)
            {
                point.setTextColor(Color.WHITE);
            }
            else
            {
                point.setTextColor(Color.parseColor("#D3D3D3"));
            }

            mPointViews[i] = point;
            mGroup.addView(mPointViews[i]);
        }

        mAdvAdapter = new ImageCycleAdapter(mContext, imageUrlList, imageCycleViewListener);
        mAdvPager.setAdapter(mAdvAdapter);
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller = new FixedSpeedScroller(mAdvPager.getContext(), new DecelerateInterpolator());
            mField.set(mAdvPager, mScroller);
            mScroller.setmDuration(800);
        } catch (Exception e) {
            e.printStackTrace();
        }
        startImageTimerTask();
    }

    /**
     * 图片轮播(手动控制自动轮播与否，便于资源控件）
     */
    public void startImageCycle()
    {
        if (null == mGroup || mGroup.getChildCount() <= 0) {
            return;
        }
        startImageTimerTask();
    }

    /**
     * 暂停轮播—用于节省资源
     */
    public void pushImageCycle()
    {
        if (null == mGroup || mGroup.getChildCount() <= 0) {
            return;
        }
        stopImageTimerTask();
    }

    public void recycle() {
        if (null != mPointViews) {
            for (int i = 0; i < mPointViews.length; i ++)
            {
                mPointViews[i] = null;
            }
            mPointViews = null;
        }
        if (null != mAdvAdapter) {
            mAdvAdapter.recycle();
        }
        mGroup.removeAllViews();
        mContext = null;
        mHandler = null;
    }

    /**
     * 图片滚动任务
     */
    private void startImageTimerTask()
    {
        stopImageTimerTask();
        if (mPointViews.length <= 1) {
            return;
        }
        isStop = false;
        // 图片滚动
        mHandler.postDelayed(mImageTimerTask, mSpaceTime);
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
            if (isStop) {
                return;
            }
            if (mPointViews != null)
            {
                mAdvPager.setCurrentItem(mAdvPager.getCurrentItem() + 1);
                if (!isStop)
                {  //if  isStop=true   //当你退出后 要把这个给停下来 不然 这个一直存在 就一直在后台循环
                    mHandler.postDelayed(mImageTimerTask, mSpaceTime);
                }

            }
        }
    };

    public ViewPager getViewPager()
    {
        return mAdvPager;
    }

    /**
     * 当你的banner放在SwipeRefreshLayout的时候，将SwipeRefreshLayout实例对象传进来避免滑动冲突
     */
    public void setSwipeRefresh(SwipeRefreshLayout mSwipeRefreshLayout)
    {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    /**
     * 轮播图片监听
     *
     * @author minking
     */
    private final class GuidePageChangeListener implements OnPageChangeListener
    {
        @Override
        public void onPageScrollStateChanged(int state)
        {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                startImageTimerTask();
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }

        @Override
        public void onPageSelected(int index)
        {
            index = index % mPointViews.length;
            // 设置当前显示的图片
            mImageIndex = index;
            // 设置图片滚动指示器背
            mPointViews[index].setTextColor(Color.WHITE);
            for (int i = 0; i < mPointViews.length; i++)
            {
                if (index != i)
                {
                    mPointViews[i].setTextColor(Color.parseColor("#D3D3D3"));
                }
            }
        }
    }

    private class ImageCycleAdapter extends PagerAdapter
    {

        /**
         * 图片视图缓存列表
         */
        private ArrayList<ImageView> mImageViewCacheList;

        /**
         * 图片资源列表
         */
        private List<String> mAdList = new ArrayList<>();

        /**
         * 广告图片点击监听
         */
        private ImageCycleViewListener mImageCycleViewListener;

        private Context mContext;

        public ImageCycleAdapter(Context context, List<String> adList, ImageCycleViewListener imageCycleViewListener)
        {
            this.mContext = context;
            this.mAdList = adList;
            mImageCycleViewListener = imageCycleViewListener;
            mImageViewCacheList = new ArrayList<>();
        }

        @Override
        public int getCount()
        {
            //return mAdList.size();
            if (mAdList.size() == 1)
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
            String imageUrl = mAdList.get(position % mAdList.size());
            ImageView imageView;
            if (mImageViewCacheList.isEmpty())
            {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 500));
                imageView.setScaleType(ScaleType.FIT_XY);
            }
            else
            {
                imageView = mImageViewCacheList.remove(0);
            }
            // 设置图片点击监听
            imageView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mImageCycleViewListener.onImageClick(position % mAdList.size(), v);
                }
            });
            imageView.setTag(imageUrl);
            container.addView(imageView);
            mImageCycleViewListener.displayImage(imageUrl, imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            ImageView view = (ImageView) object;
            mAdvPager.removeView(view);
            mImageViewCacheList.add(view);

        }

        public void recycle() {
            mAdList.clear();
            mAdList = null;
            mImageViewCacheList.clear();
            mImageViewCacheList = null;
            mContext = null;
        }
    }

}
