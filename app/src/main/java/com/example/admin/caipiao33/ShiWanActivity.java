package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.contract.IShiWanContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.presenter.ShiWanPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.LoginEvent;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//免费试玩账号登录页面
public class ShiWanActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IShiWanContract.View
{

    @BindView(R.id.shiwan_edTxt_account)
    EditText shiwanEdTxtAccount;
    @BindView(R.id.shiwan_edTxt_pwd)
    EditText shiwanEdTxtPwd;
    @BindView(R.id.shiwan_btn_mianfeishiwan)
    Button shiwanBtnMianfeishiwan;
    @BindView(R.id.shiwan_kefu_tv)
    TextView shiwanKefuTv;
    @BindView(R.id.shiwan_login_tv)
    TextView shiwanLoginTv;
    private IShiWanContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shiwan);
        ButterKnife.bind(this);
        mPresenter = new ShiWanPresenter(this, null);
        initView();
        mPresenter.getShiWan();
    }

    private void initView()
    {
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_user_login_shiwan);
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
    public void updata(String result)
    {
        shiwanEdTxtAccount.setText(result);
    }

    @Override
    public void loginOk(String result)
    {
        ToastUtil.show("登录成功！");
        setResult(Constants.REQUEST_CODE_2_LOGIN);
        EventBus.getDefault().post(new LoginEvent(""));
        finish();
    }

    @OnClick({R.id.shiwan_btn_mianfeishiwan, R.id.shiwan_kefu_tv, R.id.shiwan_login_tv})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.shiwan_btn_mianfeishiwan:
                if (StringUtils.isEmpty2(shiwanEdTxtAccount.getText().toString()))
                {
                    ToastUtil.show("请输入您的用户名！");
                    return;
                }
                if (StringUtils.isEmpty2(shiwanEdTxtPwd.getText().toString()))
                {
                    ToastUtil.show("请输入您的密码！");
                    return;
                }
                mPresenter.shiWanLogin(shiwanEdTxtAccount.getText()
                        .toString(), shiwanEdTxtPwd.getText().toString());
                break;
            case R.id.shiwan_kefu_tv:
                showLoadingDialog(false);
                HttpUtil.requestFirst("kefu", String.class, ShiWanActivity.this, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        Intent intent = new Intent(ShiWanActivity.this, WebUrlActivity.class);
                        intent.putExtra(Constants.EXTRA_URL, result);
                        intent.putExtra(Constants.EXTRA_TITLE, "在线客服");
                        startActivity(intent);
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
            case R.id.shiwan_login_tv:
                Intent intent = new Intent(ShiWanActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}

