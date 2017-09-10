package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.caipiao33.contract.IChangeMibaoContract;
import com.example.admin.caipiao33.presenter.ChangeMibaoPresenter;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设置密保
public class SetMibaoActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IChangeMibaoContract.View
{
    @BindView(R.id.setpassword_mibaowenti_et)
    EditText setpasswordMibaowentiEt;
    @BindView(R.id.setpassword_mibaodaan_et)
    EditText setpasswordMibaodaanEt;
    @BindView(R.id.btn_mibao_submit)
    Button btnMibaoSubmit;
    private IChangeMibaoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setmibao);
        ButterKnife.bind(this);
        mPresenter = new ChangeMibaoPresenter(this, null);
        initView();
    }

    private void initView()
    {
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_setting_setmibao);
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
        ToastUtil.show("密保设置成功！");
        finish();
    }

    @Override
    public void updata(String result)
    {

    }

    @OnClick(R.id.btn_mibao_submit)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_mibao_submit:
                if (StringUtils.isEmpty2(setpasswordMibaowentiEt.getText().toString()))
                {
                    ToastUtil.show("请输入密保问题！");
                    return;
                }
                if (StringUtils.isEmpty2(setpasswordMibaodaanEt.getText().toString()))
                {
                    ToastUtil.show("请输入密保答案！");
                    return;
                }
                mPresenter.setMibao(setpasswordMibaowentiEt.getText()
                        .toString(), setpasswordMibaodaanEt.getText().toString());
                break;
        }
    }
}

