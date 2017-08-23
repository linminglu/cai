package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.admin.caipiao33.contract.ISettingContract;
import com.example.admin.caipiao33.presenter.SettingPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SettingActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ISettingContract.View
{

    @BindView(R.id.setting_tuijian_rl)
    RelativeLayout settingTuijianRl;
    @BindView(R.id.setting_gonggao_rl)
    RelativeLayout settingGonggaoRl;
    @BindView(R.id.setting_touzhujilu_rl)
    RelativeLayout settingTouzhujiluRl;
    @BindView(R.id.setting_zhongjiangjilu_rl)
    RelativeLayout settingZhongjiangjiluRl;
    @BindView(R.id.setting_mingxi_rl)
    RelativeLayout settingMingxiRl;
    @BindView(R.id.setting_logout_btn)
    Button settingLogoutBtn;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ISettingContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mPresenter = new SettingPresenter(this, null);
        initView();
        mPresenter.getSettingOp();
    }

    private void initView()
    {

    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_setting);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updata(String result)
    {

    }

    @Override
    public void logoutOk(String result)
    {
        UserConfig.getInstance().clear(this);
        ToastUtil.show("退出登录成功！");
        setResult(Constants.REQUEST_CODE_Main2_LOGOUT);
        finish();
    }

    @OnClick({R.id.setting_tuijian_rl, R.id.setting_gonggao_rl, R.id.setting_touzhujilu_rl, R.id.setting_zhongjiangjilu_rl, R.id.setting_mingxi_rl, R.id.setting_logout_btn})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.setting_tuijian_rl:
                break;
            case R.id.setting_gonggao_rl:
                break;
            case R.id.setting_touzhujilu_rl:
                break;
            case R.id.setting_zhongjiangjilu_rl:
                break;
            case R.id.setting_mingxi_rl:
                break;
            case R.id.setting_logout_btn:
                mPresenter.getLogout();
                break;
        }
    }
}

