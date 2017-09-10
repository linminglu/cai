package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.caipiao33.contract.IChangePasswordContract;
import com.example.admin.caipiao33.presenter.ChangePasswordPresenter;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//修改登录密码
public class ChangePasswordActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IChangePasswordContract.View
{

    @BindView(R.id.changepassword_opassword_et)
    EditText changepasswordOpasswordEt;
    @BindView(R.id.changepassword_npassword_et)
    EditText changepasswordNpasswordEt;
    @BindView(R.id.changepassword_password_et)
    EditText changepasswordPasswordEt;
    @BindView(R.id.btn_changepassword)
    Button btnChangepassword;
    private IChangePasswordContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        ButterKnife.bind(this);
        mPresenter = new ChangePasswordPresenter(this, null);
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
        ToastUtil.show("密码修改成功！");
        finish();
    }

    @OnClick(R.id.btn_changepassword)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_changepassword:
                if (StringUtils.isEmpty2(changepasswordOpasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                }
                if (StringUtils.isEmpty2(changepasswordNpasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入新密码！");
                }
                if (StringUtils.isEmpty2(changepasswordPasswordEt.getText().toString()))
                {
                    ToastUtil.show("请再次输入新密码！");
                }
                mPresenter.changePassword(changepasswordOpasswordEt.getText()
                        .toString(), changepasswordNpasswordEt.getText()
                        .toString(), changepasswordPasswordEt.getText().toString());
                break;
        }
    }
}

