package com.example.admin.caipiao33;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.bean.TokenBean;
import com.example.admin.caipiao33.contract.IBuyContract;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.BuyPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.DateUtils;
import com.example.admin.caipiao33.utils.ServiceTime;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;
import com.example.admin.caipiao33.views.ConfirmBuyDialog;
import com.example.admin.caipiao33.views.CusRefreshLayout;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.NoScrollViewPager;
import com.example.admin.caipiao33.views.PagerSlidingTabStrip;
import com.example.admin.caipiao33.views.PopOPtions;
import com.example.admin.caipiao33.views.ResultAssist;
import com.example.admin.caipiao33.views.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
public class BuyActivity extends BaseActivity implements IBuyContract.View, Toolbar.OnMenuItemClickListener
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
    @BindView(R.id.tv_trend)
    TextView tvTrend;
    @BindView(R.id.layout_result)
    LinearLayout layoutResult;
    @BindView(R.id.buy_tab)
    PagerSlidingTabStrip buyTab;
    @BindView(R.id.buy_pager)
    NoScrollViewPager buyPager;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.swipeRefreshLayout)
    CusRefreshLayout swipeRefreshLayout;
    private String mNumber;
    private String mTitleStr;
    private String mRoomId;
    private String mPlayId;
    private String mPlayId1;
    private String mPeriod;//点开投注窗口的时候的期数
    private IBuyContract.Presenter mPresenter;
    private boolean isPause;
    private BuyRoomBean mBuyRoomBean;
    private String mEndTime;
    private String mLastOpen;
    private String[] mTitleArray;
    private FragmentManager fragmentManager;
    private ConfirmBuyDialog confirmBuyDialog;
    private ResultAssist resultAssist;
    private boolean isTitleArrayChange = false;

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
        TokenBean token = UserConfig.getInstance().getToken(this);
        if (null == token || token.getIsLogin() == 0)
        {
            // 未登录
            tvAmount.setVisibility(View.GONE);
        }
        else
        {
            mPresenter.refreshAmount();
        }
    }

    private void initView()
    {
        ButterKnife.bind(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
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
        this.mBuyRoomBean = bean;
        swipeRefreshLayout.setRefreshing(false);
        if (bean.getType().equals("xglhc") || bean.getType().equals("pcdd"))
        {
            tvTrend.setText("历史开奖");
        }
        else
        {
            tvTrend.setText("  趋势  ");
        }
        mTitleArray = getResources().getStringArray(R.array.s_array_buy);
        // 区配6合的连肖连尾
        String type = mBuyRoomBean.getType();
        buyTab.setVisibility(View.VISIBLE);
        buyPager.setNoScroll(false);
        if (type.equals("xglhc"))
        {
            if (isTitleArrayChange)
            {
                isTitleArrayChange = false;
                buyTab.notifyDataSetChanged();
            }
            String playName = mBuyRoomBean.getPlayName();
            if (playName.equals("连肖连尾"))
            {
                isTitleArrayChange = true;
                mTitleArray = new String[]{"连肖", "连尾"};
                buyTab.notifyDataSetChanged();
            }
            else if (playName.equals("自选不中") || playName.equals("连码") || playName.equals("合肖"))
            {
                buyTab.setVisibility(View.GONE);
                buyPager.setNoScroll(true);
            }
            else if (playName.equals("特码B"))
            {
                // 保留前49个数据
                List<BuyRoomBean.PlayDetailListBean> playDetailList = bean.getPlayDetailList();
                if (null != playDetailList && playDetailList.size() > 0)
                {
                    BuyRoomBean.PlayDetailListBean playDetailListBean = playDetailList.get(0);
                    if (null != playDetailListBean && null != playDetailListBean.getList())
                    {
                        int temp = 0;
                        Iterator<BuyRoomBean.PlayDetailListBean.ListBean> iterator = playDetailListBean
                                .getList()
                                .iterator();
                        while (iterator.hasNext())
                        {
                            iterator.next();
                            if (temp > 48)
                            {
                                iterator.remove();
                            }
                            temp++;
                        }
                    }
                }
            }
        }
        toolbarTitle.setText(getString(R.string.s_play_options, bean.getPlayName()));
        SpannableString ss = new SpannableString(mTitleStr + " " + mBuyRoomBean.getRoomName() + " " + bean
                .getPeriod() + "期");
        int start = mTitleStr.length() + mBuyRoomBean.getRoomName().length() + 2;
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#C30D23")), start, start + bean.getPeriod()
                .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRoom.setText(ss);
        mEndTime = bean.getEndTime();
        mLastOpen = bean.getLastOpen();
        setLotteryTime(bean.getOpentime(), bean.getEndTime());
        tvIndex.setText(bean.getLastPeriod() + "期");
        if (null == resultAssist)
        {
            resultAssist = new ResultAssist(this, getLayoutInflater(), layoutResult, bean, bean.getLastOpen());
        }
        else
        {
            resultAssist.updateData(bean.getLastOpen());
        }
        reSetPartUI();
        mHandler.post(timerRunnable);

        if (null == fragmentManager)
        {
            buyPager.setPageTransformer(true, new ZoomOutPageTransformer());
            fragmentManager = getSupportFragmentManager();
            buyPager.setAdapter(new FragmentPagerAdapter(fragmentManager)
            {

                @Override
                public Fragment getItem(int position)
                {
                    QuickBuyFragment ff = null;
                    switch (position)
                    {
                        case 0:
                            ff = QuickBuyFragment.newInstance(mBuyRoomBean, QuickBuyFragment.TYPE_SELF_SELECT);
                            ff.setRefreshLayout(swipeRefreshLayout);
                            break;
                        case 1:
                            ff = QuickBuyFragment.newInstance(mBuyRoomBean, QuickBuyFragment.TYPE_QUICK);
                            ff.setRefreshLayout(swipeRefreshLayout);
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
        }
        else
        {
            @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
            List<BuyRoomBean.PlayDetailListBean> playDetailList = new ArrayList<>();
            for (BuyRoomBean.PlayDetailListBean p : bean.getPlayDetailList())
            {
                playDetailList.add(p.clone());
            }
            int temp = 0;
            for (Fragment f : fragments)
            {
                QuickBuyFragment fragment = (QuickBuyFragment) f;
                if (temp == 1)
                {
                    bean.setPlayDetailList(playDetailList);
                }
                fragment.updateBuyRoomBean(bean);
                temp++;
            }
        }
        // 区分6 合 彩种
        if (type.equals("xglhc"))
        {
            buyPager.setCurrentItem(0);
        }
        else
        {
            buyPager.setCurrentItem(1);
        }
    }

    @Override
    public void updateLotteryData(GouCaiBean.DataBean result)
    {
        synchronized (refreshList)
        {
            refreshList.remove(mNumber);
        }
        mEndTime = result.getEndTime();
        mLastOpen = result.getLastOpen();
        // 更新期数
        mBuyRoomBean.setEndTime(mEndTime);
        mBuyRoomBean.setPeriod(result.getPeriod());
        mBuyRoomBean.setLastPeriod(result.getLastPeriod());
        SpannableString ss = new SpannableString(mTitleStr + " " + mBuyRoomBean.getRoomName() + " " + result
                .getPeriod() + "期");
        int start = mTitleStr.length() + mBuyRoomBean.getRoomName().length() + 2;
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#C30D23")), start, start + result.getPeriod()
                .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRoom.setText(ss);
        setLotteryTime(result.getOpenTime(), result.getEndTime());
        tvIndex.setText(result.getLastPeriod() + "期");
        if (null == resultAssist)
        {
            resultAssist = new ResultAssist(this, getLayoutInflater(), layoutResult, mBuyRoomBean, result
                    .getLastOpen());
        }
        else
        {
            resultAssist.updateData(result.getLastOpen());
        }
    }

    private void setLotteryTime(String openTime, String endTime)
    {
        if (StringUtils.isEmpty(openTime))
        {
            if (StringUtils.isEmpty(endTime))
            {
                endTime = "0";
            }
            tvLotteryTime.setText(getString(R.string.s_lottery_time, DateUtils.getTimeStr(Long.valueOf(endTime), "yyyy-MM-dd HH:mm:ss")));
            return;
        }
        try
        {
            tvLotteryTime.setText(getString(R.string.s_lottery_time, DateUtils.getTimeStr(Long.valueOf(openTime), "yyyy-MM-dd HH:mm:ss")));
        }
        catch (Exception e)
        {
            tvLotteryTime.setText("开奖时间：" + openTime);
        }
    }

    @Override
    public void updateLotteryFailed()
    {
        synchronized (refreshList)
        {
            refreshList.remove(mNumber);
        }
        ToastUtil.show("刷新开奖结果失败，请刷新页面");
        mHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void updateAmount(String amount)
    {
        tvAmount.setVisibility(View.VISIBLE);
        tvAmount.setText(Html.fromHtml(getString(R.string.s_amount, amount)));
    }

    @Override
    public void submitSuccess()
    {
        ToastUtil.show("下注成功");
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments)
        {
            if (fragment instanceof QuickBuyFragment)
            {
                QuickBuyFragment qf = (QuickBuyFragment) fragment;
                qf.clearChecked();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (isPause)
        {
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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (null != resultAssist)
        {
            resultAssist.clear();
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case WHAT_REFRESH_ITEM:
                    if (refreshList.contains(mNumber))
                    {
                        // 表示有内容正在刷新，不用再次请求网络
                        break;
                    }
                    synchronized (refreshList)
                    {
                        refreshList.add(mNumber);
                    }
                    mPresenter.refreshLotteryData(mNumber);
                    break;
                case WHAT_REFRESH_RESULT:
                    if (refreshList.contains(mNumber))
                    {
                        // 表示有内容正在刷新，不用再次请求网络
                        break;
                    }
                    synchronized (refreshList)
                    {
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
            if (isPause)
            {
                mHandler.removeCallbacks(this);
                return;
            }
            //            KLog.e("timerRunnable");
            reSetPartUI();
            mHandler.postDelayed(this, LIMIT_TIME);
        }
    };

    /**
     * 充值部分ui
     */
    private void reSetPartUI()
    {

        if (!StringUtils.isEmpty(mEndTime))
        {
            long lEndTime = Long.valueOf(mEndTime);
            long currentTimeMillis = ServiceTime.getInstance().getCurrentTimeMillis();
            long temp = lEndTime - currentTimeMillis;
            if (temp <= 0)
            {
                temp = 0;
            }
            long second = temp / 1000;
            if (second <= 0)
            {
                Message msg = new Message();
                msg.what = WHAT_REFRESH_ITEM;
                mHandler.sendMessage(msg);
                tvTime.setText("--:--:--");
            }
            else
            {
                String timeStr = DateUtils.getHMS(second);
                tvTime.setText(timeStr);
            }

            // 独立分支用于计时5秒发送请求开奖结果
            if (StringUtils.isEmpty(mLastOpen) && second % LOOP_TIME == 0)
            {
                Message msg = new Message();
                msg.what = WHAT_REFRESH_RESULT;
                mHandler.sendMessage(msg);
            }
        }
    }

    @OnClick({R.id.tv_clear, R.id.tv_buy, R.id.tv_trend, R.id.toolbar_title, R.id.tv_amount})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_clear: // 清空选项
                @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
                for (Fragment fragment : fragments)
                {
                    if (fragment instanceof QuickBuyFragment)
                    {
                        QuickBuyFragment qf = (QuickBuyFragment) fragment;
                        qf.clearChecked();
                    }
                }
                break;
            case R.id.tv_trend: // 历史开奖 or 趋势
                Intent webIntent = new Intent(this, WebUrlActivity.class);
                if (mBuyRoomBean.getType().equals("xglhc") || mBuyRoomBean.getType().equals("pcdd"))
                {
                    webIntent.putExtra(Constants.EXTRA_URL, HttpUtil.mNewUrl + "/api/draw/single?gid=" + mNumber + "&dayType=");
                    webIntent.putExtra(Constants.EXTRA_PLAY_NAME, mTitleStr);
                    webIntent.putExtra(Constants.EXTRA_TITLE, mTitleStr + "的历史开奖");
                }
                else
                {
                    webIntent.putExtra(Constants.EXTRA_URL, HttpUtil.mNewUrl + "/api/trend?gid=" + mNumber);
                    webIntent.putExtra(Constants.EXTRA_PLAY_NAME, mTitleStr);
                    webIntent.putExtra(Constants.EXTRA_TITLE, mTitleStr + "的趋势");
                }
                //                webIntent.putExtra(Constants.EXTRA_URL, HttpUtil.mNewUrl + "/api/trend?gid=" + mNumber);
                //                webIntent.putExtra(Constants.EXTRA_PLAY_NAME, mTitleStr);
                //                webIntent.putExtra(Constants.EXTRA_TITLE, getString(R.string.s_trend));
                startActivity(webIntent);
                break;
            case R.id.tv_buy: // 投注
                showBuyDialog();
                break;
            case R.id.toolbar_title: // 玩法选择
                showOptionsDialog();
                break;
            case R.id.tv_amount: // 刷新余额
                TokenBean token = UserConfig.getInstance().getToken(this);
                if (null == token || token.getIsLogin() == 0)
                {
                    // 未登录
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
                    return;
                }
                mPresenter.refreshAmount();
                break;
            default:
                break;
        }
    }

    private void showBuyDialog()
    {
        TokenBean token = UserConfig.getInstance().getToken(this);
        if (null == token || token.getIsLogin() == 0)
        {
            // 未登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
            return;
        }
        mPeriod = mBuyRoomBean.getPeriod();
        int currentItem = buyPager.getCurrentItem();
        @SuppressLint("RestrictedApi") QuickBuyFragment fragment = (QuickBuyFragment) fragmentManager
                .getFragments()
                .get(currentItem);
        List<BuyRoomBean.PlayDetailListBean.ListBean> checked = fragment.getChecked();
        int gridNumColumns = 4;
        // 特殊处理
        // 自选不中
        String playName = mBuyRoomBean.getPlayName();
        if (playName.equals("自选不中"))
        {
            if (null == checked)
            {
                return;
            }
            gridNumColumns = 1;
        }
        else if (playName.equals("连码"))
        {
            if (null == checked)
            {
                return;
            }
            gridNumColumns = 2;
        }
        else if (playName.equals("合肖"))
        {
            if (null == checked)
            {
                return;
            }
            gridNumColumns = 1;
        }
        else if (playName.equals("连肖连尾"))
        {
            if (null == checked)
            {
                return;
            }
            gridNumColumns = 2;
        }

        if (null == checked || checked.size() == 0)
        {
            ToastUtil.show("请选择下注的内容");
            return;
        }

        // 投注成功，选中清空，弹出提示，页面余额变化

        if (null == confirmBuyDialog)
        {
            confirmBuyDialog = new ConfirmBuyDialog(this, checked, gridNumColumns, new ConfirmBuyDialog.ConfirmBuyListener()
            {
                @Override
                public void onConfirmBuyListener(List<BuyRoomBean.PlayDetailListBean.ListBean> checked)
                {
                    StringBuilder sb = new StringBuilder();
                    for (BuyRoomBean.PlayDetailListBean.ListBean bean : checked)
                    {
                        String playName1 = bean.getPlayName();
                        playName1 = playName1.replaceAll(" ", "");
                        sb.append(playName1)
                                .append("|")
                                .append(bean.getPlayId())
                                .append("|")
                                .append(bean.getMoney())
                                .append(";");
                    }
                    mPresenter.submit(mBuyRoomBean.getNum(), mBuyRoomBean.getRoomId(), mPeriod, sb.toString());
                }
            });
        }
        else
        {
            confirmBuyDialog.setGridNumColumns(gridNumColumns);
            confirmBuyDialog.updateUI(checked);
        }
        confirmBuyDialog.show("当前第" + mPeriod + "期");
    }

    private void showOptionsDialog()
    {
        if (null == mBuyRoomBean)
        {
            return;
        }

        List<BuyRoomBean.PlayListBean> playList = mBuyRoomBean.getPlayList();
        List<String> array = new ArrayList<>();
        for (BuyRoomBean.PlayListBean bean : playList)
        {
            array.add(bean.getPlayName());
        }
        PopOPtions popOPtions = new PopOPtions();
        popOPtions.init(this, array, new PopOPtions.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(String s, int position)
            {
                BuyRoomBean.PlayListBean playListBean = mBuyRoomBean.getPlayList().get(position);
                mPlayId = playListBean.getPlayId();
                mPlayId1 = playListBean.getPlayId1();
                mPresenter.loadData(mNumber, mRoomId, mPlayId, mPlayId1);
            }
        });
        popOPtions.showPop(toolbar);
        //
        //        new MaterialDialog.Builder(this).title("玩法选择")
        //                .items(array)
        //                .positiveText(R.string.dialog_ok)
        //                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice()
        //                {
        //                    @Override
        //                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
        //                    {
        //                        if (which == -1)
        //                        {
        //                            return true;
        //                        }
        //
        //                        return true;
        //                    }
        //                })
        //                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_buy, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_home: // 首页
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mainIntent.putExtra("actionhome", true);
                startActivity(mainIntent);
                break;
            case R.id.action_record: // 投注记录
                TokenBean token = UserConfig.getInstance().getToken(this);
                if (null == token || token.getIsLogin() == 0)
                {
                    // 未登录
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
                }
                else
                {
                    startActivity(new Intent(this, BuyRecordActivity.class));
                }
                break;
            case R.id.action_new: // 最新开奖
                Intent intent = new Intent(this, WebUrlActivity.class);
                intent.putExtra(Constants.EXTRA_URL, HttpUtil.mNewUrl + "/api/draw/single?gid=" + mNumber + "&dayType=");
                intent.putExtra(Constants.EXTRA_TITLE, "最新开奖");
                startActivity(intent);
                break;
            case R.id.action_explain: // 玩法说明
                Intent webIntent = new Intent(this, WebUrlActivity.class);
                webIntent.putExtra(Constants.EXTRA_URL, HttpUtil.mNewUrl + "/api/help?gid=" + mNumber);
                webIntent.putExtra(Constants.EXTRA_TITLE, "玩法说明");
                startActivity(webIntent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.REQUEST_CODE_Main2_LOGIN && resultCode == Constants.REQUEST_CODE_2_LOGIN)
        {
            mPresenter.refreshAmount();
        }
    }

    @Override
    public void showErrorMsg(String msg)
    {
        ToastUtil.show(msg);
    }
}
