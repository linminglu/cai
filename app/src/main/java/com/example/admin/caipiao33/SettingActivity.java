package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.SettingBean;
import com.example.admin.caipiao33.contract.ISettingContract;
import com.example.admin.caipiao33.presenter.SettingPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.admin.caipiao33.R.id.setting_mibao_rl;


public class SettingActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ISettingContract.View
{
    @BindView(R.id.setting_password_rl)
    RelativeLayout settingPasswordRl;
    @BindView(R.id.setting_mibao_tv)
    TextView settingMibaoTv;
    @BindView(setting_mibao_rl)
    RelativeLayout settingMibaoRl;
    @BindView(R.id.setting_tikuanmima_tv)
    TextView settingTikuanmimaTv;
    @BindView(R.id.setting_tikuanmima_rl)
    RelativeLayout settingTikuanmimaRl;
    @BindView(R.id.setting_yinhangka_tv)
    TextView settingYinhangkaTv;
    @BindView(R.id.setting_yinhangka_rl)
    RelativeLayout settingYinhangkaRl;
    @BindView(R.id.setting_guanyu_rl)
    RelativeLayout settingGuanyuRl;
    @BindView(R.id.setting_logout_btn)
    Button settingLogoutBtn;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ISettingContract.Presenter mPresenter;
    private SettingBean settingBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mPresenter = new SettingPresenter(this, null);
        initView();
    }

    private void initView()
    {
        if (UserConfig.getInstance().getToken(SettingActivity.this).getType() == 2)
        {
            settingMibaoRl.setVisibility(View.GONE);
            settingTikuanmimaRl.setVisibility(View.GONE);
            settingYinhangkaRl.setVisibility(View.GONE);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mPresenter.getSettingOp();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getSettingOp();
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
    public void updata(SettingBean result)
    {
        swipeRefreshLayout.setRefreshing(false);
        settingBean = result;
        settingMibaoTv.setText(result.getQuestion().equals("1") ? "已设置" : "未设置");
        settingTikuanmimaTv.setText(result.getTkPasswd().equals("1") ? "已设置" : "未设置");
        settingYinhangkaTv.setText(result.getBank().equals("1") ? "已设置" : "未设置");
    }

    @Override
    public void logoutOk(String result)
    {
        UserConfig.getInstance().clear(this);
        ToastUtil.show("退出登录成功！");
        setResult(Constants.REQUEST_CODE_Main2_LOGOUT);
        finish();
    }

    @OnClick({R.id.setting_password_rl, setting_mibao_rl, R.id.setting_tikuanmima_rl, R.id.setting_yinhangka_rl, R.id.setting_guanyu_rl, R.id.setting_logout_btn})
    public void onViewClicked(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.setting_password_rl:
                intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
                break;
            case setting_mibao_rl:
                if (settingBean.getQuestion().equals("1"))
                {
                    intent = new Intent(SettingActivity.this, ChangeMibaoActivity.class);
                    startActivity(intent);
                }
                else
                {
                    intent = new Intent(SettingActivity.this, SetMibaoActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.setting_tikuanmima_rl:
                intent = new Intent(SettingActivity.this, ChangeTiKuanPasswordActivity.class);
                intent.putExtra(Constants.CHANGE_TIKUAN_PASSWORD, settingBean.getTkPasswd()
                        .equals("1") ? 1 : 0);
                startActivity(intent);
                break;
            case R.id.setting_yinhangka_rl:
                if (!settingBean.getTkPasswd().equals("1"))
                {
                    ToastUtil.show("您还没设置提款密码，请先设置提款密码再绑定银行卡！");
                    intent = new Intent(SettingActivity.this, ChangeTiKuanPasswordActivity.class);
                    intent.putExtra(Constants.CHANGE_TIKUAN_PASSWORD, settingBean.getTkPasswd()
                            .equals("1") ? 1 : 0);
                    startActivity(intent);
                }
                else
                {
                    intent = new Intent(SettingActivity.this, ChangeBankActivity.class);
                    intent.putExtra(Constants.CHANGE_TIKUAN_PASSWORD, settingBean.getBank()
                            .equals("1") ? 1 : 0);
                    startActivity(intent);
                }
                break;
            case R.id.setting_guanyu_rl:
                intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_logout_btn:
                mPresenter.getLogout();
                break;
        }
    }
}

