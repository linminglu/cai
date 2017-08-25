package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.contract.ITopupContract;
import com.example.admin.caipiao33.presenter.TopupPresenter;
import com.example.admin.caipiao33.views.PagerSlidingTabStrip;

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

    private String[] mTitleArray;
    private ITopupContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        ButterKnife.bind(this);
        mPresenter = new TopupPresenter(this, null);
        initView();
    }

    private void initView()
    {
        mTitleArray = getResources().getStringArray(R.array.s_array_topup);
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

    @Override
    public void updata(String result)
    {

    }

    @OnClick({R.id.topup_clear_tv, R.id.topup_100_tv, R.id.topup_500_tv, R.id.topup_1000_tv, R.id.topup_5000_tv, R.id.topup_10000_tv, R.id.topup_50000_tv})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.topup_clear_tv:
                break;
            case R.id.topup_100_tv:
                break;
            case R.id.topup_500_tv:
                break;
            case R.id.topup_1000_tv:
                break;
            case R.id.topup_5000_tv:
                break;
            case R.id.topup_10000_tv:
                break;
            case R.id.topup_50000_tv:
                break;
        }
    }
}

