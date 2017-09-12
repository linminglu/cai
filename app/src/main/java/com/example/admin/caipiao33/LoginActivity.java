package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.presenter.LoginPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.LoginEvent;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ILoginContract.View
{
    @BindView(R.id.edTxt_account)
    EditText edTxtAccount;
    @BindView(R.id.edTxt_pwd)
    EditText edTxtPwd;
    @BindView(R.id.cBox_password)
    CheckBox cBoxPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_find_pwd)
    TextView tvFindPwd;
    @BindView(R.id.login_weixin_img)
    ImageView loginWeixinImg;
    @BindView(R.id.login_weibo_img)
    ImageView loginWeiboImg;
    @BindView(R.id.login_qq_img)
    ImageView loginQqImg;
    @BindView(R.id.btn_mianfeishiwan)
    Button btnMianfeishiwan;
    @BindView(R.id.btn_kefu)
    Button btnKefu;

    private ILoginContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter = new LoginPresenter(this, null);
        initView();
    }

    private void initView()
    {
        cBoxPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    edTxtPwd.setTransformationMethod(null);
                }
                else
                {
                    edTxtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_user_login);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    private void login()
    {
        if (StringUtils.isEmpty2(edTxtAccount.getText().toString()))
        {
            ToastUtil.show("请输入您的用户名！");
            return;
        }
        if (StringUtils.isEmpty2(edTxtPwd.getText().toString()))
        {
            ToastUtil.show("请输入您的密码！");
            return;
        }
        mPresenter.getLogin(edTxtAccount.getText().toString(), edTxtPwd.getText().toString());
    }

    @OnClick({R.id.btn_mianfeishiwan, R.id.btn_kefu, R.id.edTxt_account, R.id.edTxt_pwd, R.id.cBox_password, R.id.btn_login, R.id.tv_register, R.id.tv_find_pwd, R.id.login_weixin_img, R.id.login_weibo_img})
    public void onViewClicked(View view)
    {
        Intent intent;
        switch (view.getId())
        {
            case R.id.edTxt_account:
                break;
            case R.id.edTxt_pwd:
                break;
            case R.id.cBox_password:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_Main2_REGISTER);
                break;
            case R.id.tv_find_pwd:
                intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_weixin_img:
                break;
            case R.id.login_weibo_img:
                break;
            case R.id.btn_mianfeishiwan:
                intent = new Intent(LoginActivity.this, ShiWanActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_kefu:
                showLoadingDialog(false);
                HttpUtil.requestFirst("kefu", String.class, LoginActivity.this, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        Intent intent = new Intent(LoginActivity.this, WebUrlActivity.class);
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
        }
    }

    @Override
    public void successFul(String result)
    {
        ToastUtil.show("登录成功！");
        setResult(Constants.REQUEST_CODE_2_LOGIN);
        EventBus.getDefault().post(new LoginEvent(""));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Constants.REQUEST_CODE_Main2_REGISTER)
        {
            edTxtAccount.setText(data.getStringExtra("username"));
        }
        if (resultCode == Constants.REQUEST_CODE_Main2_REGISTER_2LOGIN)
        {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void failed(String result)
    {
        ToastUtil.show(result);
    }
}

