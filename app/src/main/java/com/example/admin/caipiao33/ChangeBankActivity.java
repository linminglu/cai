package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.bean.ChangeBankCardBean;
import com.example.admin.caipiao33.contract.IChangeBankCardContract;
import com.example.admin.caipiao33.presenter.ChangeBankCardPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//修改银行卡
public class ChangeBankActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IChangeBankCardContract.View
{
    @BindView(R.id.changebankcard_kaihuyinhang_tv)
    TextView changebankcardKaihuyinhangTv;
    @BindView(R.id.changebankcard_zhanghao_et)
    EditText changebankcardZhanghaoEt;
    @BindView(R.id.changebankcard_kaihuren_et)
    EditText changebankcardKaihurenEt;
    @BindView(R.id.changebankcard_shengfen_et)
    EditText changebankcardShengfenEt;
    @BindView(R.id.changebankcard_city_et)
    EditText changebankcardCityEt;
    @BindView(R.id.changebankcard_password_et)
    EditText changebankcardPasswordEt;
    @BindView(R.id.btn_changebankcard)
    Button btnChangebankcard;
    @BindView(R.id.changebankcard_otherbank_et)
    EditText changebankcardOtherbankEt;
    @BindView(R.id.changebankcard_otherbank_ll)
    LinearLayout changebankcardOtherbankLl;
    private IChangeBankCardContract.Presenter mPresenter;
    private int hasbank;
    private String kaihubank;
    private String[] banknames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changebankcardpassword);
        banknames = getResources().getStringArray(R.array.s_array_banknames);
        ButterKnife.bind(this);
        mPresenter = new ChangeBankCardPresenter(this, null);
        hasbank = getIntent().getIntExtra(Constants.CHANGE_TIKUAN_PASSWORD, 0);
        if (hasbank == 1)
        {
            changebankcardKaihurenEt.setEnabled(false);
            mPresenter.getBankCard();
        }
        initView();
    }

    private void initView()
    {
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_setting_changebank);
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
        ToastUtil.show("银行卡修改成功！");
        finish();
    }

    @Override
    public void updata(ChangeBankCardBean result)
    {
        kaihubank = result.getBankName();
        changebankcardKaihuyinhangTv.setText(result.getBankName());
        changebankcardZhanghaoEt.setText(result.getAccountCode());
        changebankcardKaihurenEt.setText(result.getAccountName());
        changebankcardShengfenEt.setText(result.getProvice());
        changebankcardCityEt.setText(result.getCity());
    }

    @OnClick({R.id.changebankcard_kaihuyinhang_tv, R.id.btn_changebankcard})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.changebankcard_kaihuyinhang_tv:
                showOptionsDialog();
                break;
            case R.id.btn_changebankcard:
                if (StringUtils.isEmpty2(kaihubank))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (StringUtils.isEmpty2(changebankcardZhanghaoEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (StringUtils.isEmpty2(changebankcardKaihurenEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (StringUtils.isEmpty2(changebankcardShengfenEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (StringUtils.isEmpty2(changebankcardCityEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (StringUtils.isEmpty2(changebankcardPasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入新密码！");
                    return;
                }
                if (kaihubank.equals("其他"))
                {
                    if (StringUtils.isEmpty2(changebankcardOtherbankEt.getText().toString()))
                    {
                        ToastUtil.show("请输入其他银行的名称！");
                        return;
                    }
                    mPresenter.changeBankCard(changebankcardPasswordEt.getText()
                            .toString(), changebankcardKaihurenEt.getText()
                            .toString(), changebankcardZhanghaoEt.getText()
                            .toString(), changebankcardOtherbankEt.getText()
                            .toString(), changebankcardShengfenEt.getText()
                            .toString(), changebankcardCityEt.getText().toString(), "other");
                }
                else
                {

                    mPresenter.changeBankCard(changebankcardPasswordEt.getText()
                            .toString(), changebankcardKaihurenEt.getText()
                            .toString(), changebankcardZhanghaoEt.getText()
                            .toString(), kaihubank, changebankcardShengfenEt.getText()
                            .toString(), changebankcardCityEt.getText().toString(), "");
                }
                break;
        }
    }

    private void showOptionsDialog()
    {
        if (null == banknames)
        {
            return;
        }
        new MaterialDialog.Builder(ChangeBankActivity.this).title("请选择银行")
                .items(banknames)
                .positiveText(R.string.dialog_ok)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice()
                {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
                    {
                        if (which != -1)
                        {
                            kaihubank = banknames[which];
                            changebankcardKaihuyinhangTv.setText(banknames[which]);
                            changebankcardOtherbankLl.setVisibility(View.GONE);

                            if (banknames[which].equals("其他"))
                            {
                                //TODO
                                changebankcardOtherbankLl.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    }
                })
                .show();
    }
}

