package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannedString;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IBuyContract;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.presenter.BuyPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.DateUtils;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.PagerSlidingTabStrip;
import com.example.admin.caipiao33.views.ZoomOutPageTransformer;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 调用者需要传入房间id
 * 彩票类型 title -- Constants.EXTRA_TITLE
 * 彩票编码 num -- Constants.EXTRA_NUMBER
 * 房间id  roomid -- Constants.EXTRA_ROOM_ID
 * 玩法id  playId -- Constants.EXTRA_PLAY_ID
 * 玩法id1 playId1 -- Constants.EXTRA_PLAY_ID1
 * <p>
 * 两种情况
 * room
 * -- 显示房间列表，再次选择一项进入page页面
 * page
 * -- 立即购买页面
 */
public class BuyActivity extends BaseActivity implements IBuyContract.View
{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_room)
    TextView tvRoom;
    @BindView(R.id.tv_lottery_time)
    TextView tvLotteryTime;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.buy_tab)
    PagerSlidingTabStrip buyTab;
    @BindView(R.id.buy_pager)
    ViewPager buyPager;
    private String mNumber;
    private String mTitleStr;
    private String mRoomId;
    private String mPlayId;
    private String mPlayId1;
    private IBuyContract.Presenter mPresenter;
    private boolean isPause;
    private BuyRoomBean mBuyRoomBean;
    private String mEndTime;
    private String mLastOpen;
    private String[] mTitleArray;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initView();
        Intent intent = getIntent();
        mNumber = intent.getStringExtra(Constants.EXTRA_NUMBER);
        mTitleStr = intent.getStringExtra(Constants.EXTRA_TITLE);
        mRoomId = intent.getStringExtra(Constants.EXTRA_ROOM_ID);
        mPlayId = intent.getStringExtra(Constants.EXTRA_PLAY_ID);
        mPlayId1 = intent.getStringExtra(Constants.EXTRA_PLAY_ID1);

        mPresenter = new BuyPresenter(this);
        mPresenter.loadData(mNumber, mRoomId, mPlayId, mPlayId1);
    }

    private void initView()
    {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.loadData(mNumber, mRoomId, mPlayId, mPlayId1);
            }
        });
        mTitleArray = getResources().getStringArray(R.array.s_array_buy);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateHomePage(BuyRoomBean bean)
    {
        this.mBuyRoomBean = bean;
        SpannedString ss = new SpannedString(mTitleStr + bean.getRoomName() + bean.getPeriod() + "期");
        tvRoom.setText(ss);
        tvLotteryTime.setText(getString(R.string.s_lottery_time, DateUtils.getTimeStr(Long.valueOf(bean.getEndTime()), "yyyy-MM-dd HH:mm:ss")));
        tvIndex.setText(bean.getLastPeriod() + "期");
        String lastOpen = bean.getLastOpen();
        if (StringUtils.isEmpty(lastOpen)) {
            tvResult.setText("等待开奖");
        } else {
            tvResult.setText(lastOpen);
        }
        mEndTime = bean.getEndTime();
        mLastOpen = bean.getLastOpen();
        reSetPartUI();
        mHandler.post(timerRunnable);


        buyPager.setPageTransformer(true, new ZoomOutPageTransformer());
        fragmentManager = getSupportFragmentManager();
        buyPager.setAdapter(new FragmentPagerAdapter(fragmentManager)
        {

            @Override
            public Fragment getItem(int position)
            {
                Fragment ff = null;
                switch (position)
                {
                    case 0:
                        ff = QuickBuyFragment.newInstance(mBuyRoomBean);
                        break;
                    case 1:
                        ff = QuickBuyFragment.newInstance(mBuyRoomBean);
                        break;
                }
                return ff;
            }

            @Override
            public int getCount()
            {
                return mTitleArray.length;
            }

            @Override
            public CharSequence getPageTitle(int position)
            {
                return mTitleArray[position];
            }
        });
        buyTab.setViewPager(buyPager);
        buyPager.setCurrentItem(1);
    }

    @Override
    public void updateLotteryData(GouCaiBean.DataBean result)
    {
        synchronized (refreshList) {
            refreshList.remove(mNumber);
        }
        mEndTime = result.getEndTime();
        mLastOpen = result.getLastOpen();
        SpannedString ss = new SpannedString(mTitleStr + mBuyRoomBean.getRoomName() + result.getPeriod() + "期");
        tvRoom.setText(ss);
        tvLotteryTime.setText(getString(R.string.s_lottery_time, DateUtils.getTimeStr(Long.valueOf(mEndTime), "yyyy-MM-dd HH:mm:ss")));
        tvIndex.setText(result.getLastPeriod() + "期");
        String lastOpen = result.getLastOpen();
        if (StringUtils.isEmpty(lastOpen)) {
            tvResult.setText("等待开奖");
        } else {
            tvResult.setText(lastOpen);
        }
    }

    @Override
    public void updateLotteryFailed()
    {
        synchronized (refreshList) {
            refreshList.remove(mNumber);
        }
        ToastUtil.show("刷新开奖结果失败，请刷新页面");
        mHandler.removeCallbacks(timerRunnable);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (isPause) {
            // 页面暂停过，需要重新加载
            isPause = false;
            mPresenter.loadData(mNumber, mRoomId, mPlayId, mPlayId1);
            return;
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isPause = true;
        mHandler.removeCallbacks(timerRunnable);
    }

    private Handler mHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what) {
                case WHAT_REFRESH_ITEM:
                    if (refreshList.contains(mNumber)) {
                        // 表示有内容正在刷新，不用再次请求网络
                        break;
                    }
                    synchronized (refreshList) {
                        refreshList.add(mNumber);
                    }
                    mPresenter.refreshLotteryData(mNumber);
                    break;
                case WHAT_REFRESH_RESULT:
                    if (refreshList.contains(mNumber)) {
                        // 表示有内容正在刷新，不用再次请求网络
                        break;
                    }
                    synchronized (refreshList) {
                        refreshList.add(mNumber);
                    }
                    mPresenter.refreshLotteryData(mNumber);
                    break;
            }
            return false;
        }
    });

    private static final int LIMIT_TIME = 1000; // 定时器1秒刷新页面
    private static final int LOOP_TIME = 5; // 定时每隔5秒请求一次开奖结果
    private static final int WHAT_REFRESH_ITEM = 101; // 用于刷新单个项目
    private static final int WHAT_REFRESH_RESULT = 102; // 用于刷新开奖结果

    // 存储请求刷新数据的列表
    private List<String> refreshList = new ArrayList<>();
    private Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            if (isPause) {
                mHandler.removeCallbacks(this);
                return;
            }
            KLog.e("timerRunnable");
            reSetPartUI();
            mHandler.postDelayed(this, LIMIT_TIME);
        }
    };

    /**
     * 充值部分ui
     */
    private void reSetPartUI()
    {
        long currentTimeMillis = System.currentTimeMillis();

        if (!StringUtils.isEmpty(mEndTime)) {
            long lEndTime = Long.valueOf(mEndTime);
            long temp = lEndTime - currentTimeMillis;
            if (temp <= 0) {
                temp = 0;
            }
            long second = temp / 1000;
            if (second <= 0) {
                Message msg = new Message();
                msg.what = WHAT_REFRESH_ITEM;
                mHandler.sendMessage(msg);
                tvTime.setText("--:--:--");
            } else {
                String timeStr = DateUtils.getHMS(second);
                tvTime.setText(timeStr);
            }

            // 独立分支用于计时5秒发送请求开奖结果
            if (StringUtils.isEmpty(mLastOpen) && second % LOOP_TIME == 0) {
                Message msg = new Message();
                msg.what = WHAT_REFRESH_RESULT;
                mHandler.sendMessage(msg);
            }
        }
    }
}
