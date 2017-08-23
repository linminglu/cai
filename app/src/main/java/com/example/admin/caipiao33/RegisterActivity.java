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

import com.example.admin.caipiao33.bean.RegisterBean;
import com.example.admin.caipiao33.bean.RegisterSubmitBean;
import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.contract.IRegisterContract;
import com.example.admin.caipiao33.presenter.LoginPresenter;
import com.example.admin.caipiao33.presenter.RegisterPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RegisterActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IRegisterContract.View
{

    private IRegisterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mPresenter = new RegisterPresenter(this, null);
        initView();
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

    }

    @Override
    public void updataVerifycode(RegisterBean result)
    {

    }

    @Override
    public void registerOk(RegisterSubmitBean result)
    {

    }
}

