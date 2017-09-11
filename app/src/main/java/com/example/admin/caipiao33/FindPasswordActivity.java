package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.caipiao33.contract.IFindPasswordContract;
import com.example.admin.caipiao33.presenter.FindPasswordPresenter;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//验证密保
public class FindPasswordActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IFindPasswordContract.View
{
    @BindView(R.id.findpassword_name_et)
    EditText findpasswordNameEt;
    @BindView(R.id.findpassword_mibaowenti_et)
    EditText findpasswordMibaowentiEt;
    @BindView(R.id.findpassword_mibaodaan_et)
    EditText findpasswordMibaodaanEt;
    @BindView(R.id.btn_mibao_submit)
    Button btnMibaoSubmit;
    private IFindPasswordContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpassword);
        ButterKnife.bind(this);
        mPresenter = new FindPasswordPresenter(this, null);
        initView();
    }

    private void initView()
    {
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_setting_changemibao);
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
        ToastUtil.show("密保验证成功！");
        Intent intent = new Intent(FindPasswordActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_mibao_submit)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_mibao_submit:
                if (StringUtils.isEmpty2(findpasswordNameEt.getText().toString()))
                {
                    ToastUtil.show("请输入用户名！");
                    return;
                }
                if (StringUtils.isEmpty2(findpasswordMibaowentiEt.getText().toString()))
                {
                    ToastUtil.show("请输入密保问题！");
                    return;
                }
                if (StringUtils.isEmpty2(findpasswordMibaodaanEt.getText().toString()))
                {
                    ToastUtil.show("请输入密保答案！");
                    return;
                }
                mPresenter.getPassword(findpasswordNameEt.getText()
                        .toString(), findpasswordMibaowentiEt.getText()
                        .toString(), findpasswordMibaodaanEt.getText().toString());
                break;
        }
    }
}

