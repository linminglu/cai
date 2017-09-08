package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.TopupBean;
import com.example.admin.caipiao33.contract.ITopupContract;
import com.example.admin.caipiao33.fragment.AliPayFragment;
import com.example.admin.caipiao33.fragment.BankPayFragment;
import com.example.admin.caipiao33.fragment.OnLinePayFragment;
import com.example.admin.caipiao33.fragment.QqPayFragment;
import com.example.admin.caipiao33.fragment.WeiXinPayFragment;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.presenter.TopupPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.TopupEvent;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.NumberInputFilter;
import com.example.admin.caipiao33.views.PagerSlidingTabStrip;
import com.example.admin.caipiao33.views.ZoomOutPageTransformer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//充值
public class TopupActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ITopupContract.View
{

    @BindView(R.id.topup_username_tv)
    TextView topupUsernameTv;
    @BindView(R.id.topup_yue_tv)
    TextView topupYueTv;
    @BindView(R.id.topup_money_et)
    EditText topupMoneyEt;
    @BindView(R.id.topup_clear_tv)
    TextView topupClearTv;
    @BindView(R.id.topup_100_tv)
    TextView topup100Tv;
    @BindView(R.id.topup_500_tv)
    TextView topup500Tv;
    @BindView(R.id.topup_1000_tv)
    TextView topup1000Tv;
    @BindView(R.id.topup_5000_tv)
    TextView topup5000Tv;
    @BindView(R.id.topup_10000_tv)
    TextView topup10000Tv;
    @BindView(R.id.topup_50000_tv)
    TextView topup50000Tv;
    @BindView(R.id.topup_subtitle_tv)
    TextView topupSubtitleTv;
    @BindView(R.id.topup_buy_psts)
    PagerSlidingTabStrip topupBuyPsts;
    @BindView(R.id.topup_buy_vp)
    ViewPager topupBuyVp;


    private FragmentManager fragmentManager;

    private String[] mTitleArray;
    private ITopupContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mPresenter = new TopupPresenter(this, null);
        mPresenter.getTopup();
        initView();
    }

    private void initView()
    {
        mTitleArray = getResources().getStringArray(R.array.s_array_topup);
        InputFilter[] filters = {new NumberInputFilter()};
        topupMoneyEt.setFilters(filters);

        topupBuyVp.setOffscreenPageLimit(5);
        topupBuyVp.setPageTransformer(true, new ZoomOutPageTransformer());
        fragmentManager = getSupportFragmentManager();
        topupBuyVp.setAdapter(new FragmentPagerAdapter(fragmentManager)
        {

            @Override
            public Fragment getItem(int position)
            {
                Fragment ff = null;
                switch (position)
                {
                    case 0:
                        ff = new BankPayFragment();
                        break;
                    case 1:
                        ff = new WeiXinPayFragment();
                        break;
                    case 2:
                        ff = new AliPayFragment();
                        break;
                    case 3:
                        ff = new QqPayFragment();
                        break;
                    case 4:
                        ff = new OnLinePayFragment();
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
        topupBuyPsts.setViewPager(topupBuyVp);

        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.getTopup();
            }
        });
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_topup);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_jilu: // 充值记录
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, TopupActivity.this, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        toWebUrlActivity(result, "在线客服");
                    }

                    @Override
                    public void onFailed(int code, String msg)
                    {

                    }

                    @Override
                    public void onFinish()
                    {
                        hideLoadingDialog();
                    }
                }, null);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_topup, menu);
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(TopupEvent messageEvent)
    {
        finish();
    }

    @Override
    public void updata(TopupBean result)
    {
        topupUsernameTv.setText(result.getCode());
        topupYueTv.setText(result.getAmount() + "元");
    }

    @OnClick({R.id.topup_clear_tv, R.id.topup_100_tv, R.id.topup_500_tv, R.id.topup_1000_tv, R.id.topup_5000_tv, R.id.topup_10000_tv, R.id.topup_50000_tv})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.topup_clear_tv:
                topupMoneyEt.setText("");
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
            case R.id.topup_100_tv:
                topupMoneyEt.setText(updateMoney(100));
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
            case R.id.topup_500_tv:
                topupMoneyEt.setText(updateMoney(500));
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
            case R.id.topup_1000_tv:
                topupMoneyEt.setText(updateMoney(1000));
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
            case R.id.topup_5000_tv:
                topupMoneyEt.setText(updateMoney(5000));
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
            case R.id.topup_10000_tv:
                topupMoneyEt.setText(updateMoney(10000));
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
            case R.id.topup_50000_tv:
                topupMoneyEt.setText(updateMoney(50000));
                topupMoneyEt.setSelection(topupMoneyEt.length());
                break;
        }
    }

    private String updateMoney(int num)
    {
        try
        {
            int afterNum = Integer.valueOf(topupMoneyEt.getText().toString());
            return afterNum + num + "";
        }
        catch (Exception e)
        {

        }
        return num + "";
    }

    public int getTopupAmount()
    {
        int amount = 0;
        try
        {
            amount = Integer.valueOf(topupMoneyEt.getText().toString());
        }
        catch (Exception e)
        {

        }
        return amount;
    }

    // 跳转到网页
    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(TopupActivity.this, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }
}

