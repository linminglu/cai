package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.RegisterBean;
import com.example.admin.caipiao33.bean.RegisterSubmitBean;
import com.example.admin.caipiao33.contract.IRegisterContract;
import com.example.admin.caipiao33.presenter.RegisterPresenter;
import com.example.admin.caipiao33.utils.CodeUtils;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IRegisterContract.View
{

    @BindView(R.id.register_username_et)
    EditText registerUsernameEt;
    @BindView(R.id.register_username_ll)
    LinearLayout registerUsernameLl;
    @BindView(R.id.register_password_et)
    EditText registerPasswordEt;
    @BindView(R.id.register_password_ll)
    LinearLayout registerPasswordLl;
    @BindView(R.id.register_tjr_et)
    EditText registerTjrEt;
    @BindView(R.id.register_tjr_ll)
    LinearLayout registerTjrLl;
    @BindView(R.id.register_phone_et)
    EditText registerPhoneEt;
    @BindView(R.id.register_phone_ll)
    LinearLayout registerPhoneLl;
    @BindView(R.id.register_email_et)
    EditText registerEmailEt;
    @BindView(R.id.register_email_ll)
    LinearLayout registerEmailLl;
    @BindView(R.id.register_qq_et)
    EditText registerQqEt;
    @BindView(R.id.register_qq_ll)
    LinearLayout registerQqLl;
    @BindView(R.id.register_vcode_et)
    EditText registerVcodeEt;
    @BindView(R.id.register_vcode_iv)
    ImageView registerVcodeIv;
    @BindView(R.id.register_vcode_ll)
    LinearLayout registerVcodeLl;
    @BindView(R.id.register_protocol_cb)
    CheckBox registerProtocolCb;
    @BindView(R.id.register_protocol_tv)
    TextView registerProtocolTv;
    @BindView(R.id.register_btn)
    Button registerBtn;
    private IRegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this, null);
        initView();
        mPresenter.getRegisterOp();
    }

    private void initView()
    {

    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_register);
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
    public void updata(RegisterBean result)
    {
        if (result.getPhone() == 0)
        {
            registerPhoneLl.setVisibility(View.GONE);
        }
        else
        {
            registerPhoneLl.setVisibility(View.VISIBLE);
            if (result.getPhone() == 1)
            {
                registerPhoneEt.setHint("请输入手机号码（选填）");
            }
            else
            {
                registerPhoneEt.setHint("请输入手机号码（必填）");
            }
        }
        if (result.getEmail() == 0)
        {
            registerEmailLl.setVisibility(View.GONE);
        }
        else
        {
            registerEmailLl.setVisibility(View.VISIBLE);
            if (result.getEmail() == 1)
            {
                registerEmailEt.setHint("请输入邮箱地址（选填）");
            }
            else
            {
                registerEmailEt.setHint("请输入邮箱地址（必填）");
            }
        }
        if (result.getQq() == 0)
        {
            registerQqLl.setVisibility(View.GONE);
        }
        else
        {
            registerQqLl.setVisibility(View.VISIBLE);
            if (result.getQq() == 1)
            {
                registerQqEt.setHint("请输入QQ号码（选填）");
            }
            else
            {
                registerQqEt.setHint("请输入QQ号码（必填）");
            }
        }
        registerVcodeIv.setImageBitmap(CodeUtils.getInstance()
                .createBitmap(result.getVerifyCode()));
    }

    @Override
    public void updataVerifycode(RegisterBean result)
    {
        registerVcodeIv.setImageBitmap(CodeUtils.getInstance()
                .createBitmap(result.getVerifyCode()));
    }

    @Override
    public void registerOk(RegisterSubmitBean result)
    {
        ToastUtil.show("注册成功！");
        Intent intent = new Intent();
        intent.putExtra("username", registerUsernameEt.getText().toString());
        setResult(Constants.REQUEST_CODE_Main2_REGISTER, intent);
        finish();
    }

    @OnClick({R.id.register_vcode_iv, R.id.register_protocol_cb, R.id.register_protocol_tv, R.id.register_btn})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.register_vcode_iv:
                mPresenter.getVerifycode();
                break;
            case R.id.register_protocol_cb:
                break;
            case R.id.register_protocol_tv:
                break;
            case R.id.register_btn:
                if (registerProtocolCb.isChecked())
                {
                    mPresenter.submitRegister(registerUsernameEt.getText()
                            .toString(), registerPasswordEt.getText()
                            .toString(), registerVcodeEt.getText()
                            .toString(), registerTjrEt.getText().toString(), registerQqEt.getText()
                            .toString(), registerPhoneEt.getText()
                            .toString(), registerEmailEt.getText().toString());
                }
                else
                {
                    ToastUtil.show("请同意注册协议！");
                }
                break;
        }
    }
}

