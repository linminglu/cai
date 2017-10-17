package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.caipiao33.contract.IChangeTiKuanPasswordContract;
import com.example.admin.caipiao33.presenter.ChangeTiKuanPasswordPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//修改提款密码
public class ChangeTiKuanPasswordActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IChangeTiKuanPasswordContract.View
{
    @BindView(R.id.changetikuanpassword_opassword_et)
    EditText changetikuanpasswordOpasswordEt;
    @BindView(R.id.changetikuanpassword_npassword_et)
    EditText changetikuanpasswordNpasswordEt;
    @BindView(R.id.btn_changetikuanpassword)
    Button btnChangetikuanpassword;
    private IChangeTiKuanPasswordContract.Presenter mPresenter;
    private int haspassword;
    private int fromtikuan = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changetikuanpassword);
        fromtikuan = getIntent().getIntExtra("fromtikuan", 0);
        ButterKnife.bind(this);
        mPresenter = new ChangeTiKuanPasswordPresenter(this, null);
        haspassword = getIntent().getIntExtra(Constants.CHANGE_TIKUAN_PASSWORD, 1);
        initView();
    }

    private void initView()
    {
        if (haspassword == 0)
        {
            changetikuanpasswordOpasswordEt.setText("1111");
            changetikuanpasswordOpasswordEt.setEnabled(false);
        }
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_setting_changetikuanpassword);
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
        ToastUtil.show("提款密码修改成功！");
        if (fromtikuan == 0)
        {
            finish();
        }
        else
        {
            Intent intent = new Intent(ChangeTiKuanPasswordActivity.this, ChangeBankActivity.class);
            intent.putExtra(Constants.CHANGE_TIKUAN_PASSWORD, 0);
            startActivity(intent);
        }
    }

    @Override
    public void updata(String result)
    {

    }

    @OnClick(R.id.btn_changetikuanpassword)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_changetikuanpassword:
                if (StringUtils.isEmpty2(changetikuanpasswordOpasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (StringUtils.isEmpty2(changetikuanpasswordNpasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入新密码！");
                    return;
                }
                mPresenter.changeTiKuanPassword(changetikuanpasswordOpasswordEt.getText()
                        .toString(), changetikuanpasswordNpasswordEt.getText().toString());
                break;
        }
    }
}

