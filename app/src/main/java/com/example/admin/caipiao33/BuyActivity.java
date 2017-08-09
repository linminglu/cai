package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannedString;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.contract.IBuyContract;
import com.example.admin.caipiao33.presenter.BuyPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.PagerSlidingTabStrip;

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
        SpannedString ss = new SpannedString(mTitleStr + bean.getRoomName() + bean.getPeriod() + "期");
        tvRoom.setText(ss);

    }
}
