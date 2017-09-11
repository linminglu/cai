package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.caipiao33.contract.IResetPasswordContract;
import com.example.admin.caipiao33.presenter.ResetPasswordPresenter;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//重置登录密码
public class ResetPasswordActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IResetPasswordContract.View
{
    @BindView(R.id.resetpassword_npassword_et)
    EditText resetpasswordNpasswordEt;
    @BindView(R.id.resetpassword_password_et)
    EditText resetpasswordPasswordEt;
    @BindView(R.id.btn_resetpassword)
    Button btnResetpassword;
    private IResetPasswordContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        ButterKnife.bind(this);
        mPresenter = new ResetPasswordPresenter(this, null);
        initView();
    }

    private void initView()
    {
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_setting_changepassword);
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
    public void successFul(String result)
    {
        ToastUtil.show("密码重置成功！");
        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_resetpassword)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_resetpassword:
                if (StringUtils.isEmpty2(resetpasswordNpasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入新密码！");
                    return;
                }
                if (StringUtils.isEmpty2(resetpasswordPasswordEt.getText().toString()))
                {
                    ToastUtil.show("请再次输入新密码！");
                    return;
                }
                mPresenter.resetPassword(resetpasswordNpasswordEt.getText()
                        .toString(), resetpasswordPasswordEt.getText().toString());
                break;
        }
    }
}

