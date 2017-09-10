package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.contract.IChangeMibaoContract;
import com.example.admin.caipiao33.presenter.ChangeMibaoPresenter;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//设置密保
public class ChangeMibaoActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IChangeMibaoContract.View
{
    @BindView(R.id.changepassword_mibaowenti_tv)
    TextView changepasswordMibaowentiTv;
    @BindView(R.id.changepassword_mibaowenti_et)
    EditText changepasswordMibaowentiEt;
    @BindView(R.id.changepassword_mibaodaan_et)
    EditText changepasswordMibaodaanEt;
    @BindView(R.id.btn_mibao_submit)
    Button btnMibaoSubmit;
    private IChangeMibaoContract.Presenter mPresenter;
    private String question;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemibao);
        ButterKnife.bind(this);
        mPresenter = new ChangeMibaoPresenter(this, null);
        mPresenter.getMibao();
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
        ToastUtil.show("密保修改成功！");
        finish();
    }

    @Override
    public void updata(String result)
    {
        question = result;
        if (!StringUtils.isEmpty2(question))
        {
            changepasswordMibaowentiTv.setText(question);
        }
        else
        {
            finish();
        }
    }

    @OnClick(R.id.btn_mibao_submit)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_mibao_submit:
                if (StringUtils.isEmpty2(changepasswordMibaowentiEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧问题！");
                }
                if (StringUtils.isEmpty2(changepasswordMibaodaanEt.getText().toString()))
                {
                    ToastUtil.show("请输入新问题！");
                }
                mPresenter.changeMibao(question, changepasswordMibaowentiEt.getText()
                        .toString(), changepasswordMibaodaanEt.getText().toString());
                break;
        }
    }
}

