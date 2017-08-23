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
import com.example.admin.caipiao33.fragment.UserFragment;
import com.example.admin.caipiao33.presenter.LoginPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.admin.caipiao33.R.id.edTxt_account;


public class LoginActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ILoginContract.View
{
    @BindView(edTxt_account)
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

    @OnClick({edTxt_account, R.id.edTxt_pwd, R.id.cBox_password, R.id.btn_login, R.id.tv_register, R.id.tv_find_pwd, R.id.login_weixin_img, R.id.login_weibo_img})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case edTxt_account:
                break;
            case R.id.edTxt_pwd:
                break;
            case R.id.cBox_password:
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_Main2_REGISTER);
                break;
            case R.id.tv_find_pwd:
                break;
            case R.id.login_weixin_img:
                break;
            case R.id.login_weibo_img:
                break;
        }
    }

    @Override
    public void successFul(String result)
    {
        ToastUtil.show("登录成功！");
        setResult(Constants.REQUEST_CODE_2_LOGIN);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Constants.REQUEST_CODE_Main2_REGISTER)
        {
            edTxtAccount.setText(data.getStringExtra("username"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void failed(String result)
    {
        ToastUtil.show(result);
    }
}

