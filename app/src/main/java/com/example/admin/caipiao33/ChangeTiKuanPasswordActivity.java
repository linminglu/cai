package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
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
    @BindView(R.id.changetikuanpassword_npassword_tv1)
    TextView changetikuanpasswordNpasswordTv1;
    @BindView(R.id.changetikuanpassword_npassword_tv2)
    TextView changetikuanpasswordNpasswordTv2;
    @BindView(R.id.changetikuanpassword_npassword_tv3)
    TextView changetikuanpasswordNpasswordTv3;
    @BindView(R.id.changetikuanpassword_npassword_tv4)
    TextView changetikuanpasswordNpasswordTv4;
    private IChangeTiKuanPasswordContract.Presenter mPresenter;
    private int haspassword;
    private int fromtikuan = 0;
    private String[] passwordnums;
    private String title;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changetikuanpassword);
        passwordnums = getResources().getStringArray(R.array.nums);
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
            title = "设置提现密码";
        }
        else
        {
            title = "修改提现密码";
        }
        toolbar.setTitle(title);
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        this.toolbar = toolbar;
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

    @OnClick({R.id.btn_changetikuanpassword, R.id.changetikuanpassword_npassword_tv1, R.id.changetikuanpassword_npassword_tv2, R.id.changetikuanpassword_npassword_tv3, R.id.changetikuanpassword_npassword_tv4})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_changetikuanpassword:
                String npw = changetikuanpasswordNpasswordTv1.getText()
                        .toString() + changetikuanpasswordNpasswordTv2.getText()
                        .toString() + changetikuanpasswordNpasswordTv3.getText()
                        .toString() + changetikuanpasswordNpasswordTv4.getText().toString();
                //                if (StringUtils.isEmpty2(changetikuanpasswordOpasswordEt.getText().toString()))
                //                {
                //                    ToastUtil.show("请输入旧密码！");
                //                    return;
                //                }
                //                if (StringUtils.isEmpty2(changetikuanpasswordNpasswordEt.getText().toString()))
                //                {
                //                    ToastUtil.show("请输入新密码！");
                //                    return;
                //                }
                if (StringUtils.isEmpty2(changetikuanpasswordOpasswordEt.getText().toString()))
                {
                    ToastUtil.show("请输入旧密码！");
                    return;
                }
                if (npw.length() < 4)
                {
                    ToastUtil.show("请输入完整的新密码！");
                    return;
                }
                mPresenter.changeTiKuanPassword(changetikuanpasswordOpasswordEt.getText()
                        .toString(), npw);
                break;
            case R.id.changetikuanpassword_npassword_tv1:
                if (StringUtils.isEmpty2(changetikuanpasswordNpasswordTv1.getText().toString()))
                {
                    showOptionsDialog(-1, 1);
                }
                else
                {
                    showOptionsDialog(Integer.valueOf(changetikuanpasswordNpasswordTv1.getText()
                            .toString()), 1);
                }
                break;
            case R.id.changetikuanpassword_npassword_tv2:
                if (StringUtils.isEmpty2(changetikuanpasswordNpasswordTv2.getText().toString()))
                {
                    showOptionsDialog(-1, 2);
                }
                else
                {
                    showOptionsDialog(Integer.valueOf(changetikuanpasswordNpasswordTv2.getText()
                            .toString()), 2);
                }
                break;
            case R.id.changetikuanpassword_npassword_tv3:
                if (StringUtils.isEmpty2(changetikuanpasswordNpasswordTv3.getText().toString()))
                {
                    showOptionsDialog(-1, 3);
                }
                else
                {
                    showOptionsDialog(Integer.valueOf(changetikuanpasswordNpasswordTv3.getText()
                            .toString()), 3);
                }
                break;
            case R.id.changetikuanpassword_npassword_tv4:
                if (StringUtils.isEmpty2(changetikuanpasswordNpasswordTv4.getText().toString()))
                {
                    showOptionsDialog(-1, 4);
                }
                else
                {
                    showOptionsDialog(Integer.valueOf(changetikuanpasswordNpasswordTv4.getText()
                            .toString()), 4);
                }
                break;
        }
    }

    private void showOptionsDialog(int weizhi, final int mwhich)
    {
        new MaterialDialog.Builder(ChangeTiKuanPasswordActivity.this).title("请选择")
                .items(passwordnums)
                .positiveText(R.string.dialog_ok)
                .negativeText(R.string.dialog_cancel)
                .itemsCallbackSingleChoice(weizhi, new MaterialDialog.ListCallbackSingleChoice()
                {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
                    {
                        if (which != -1)
                        {
                            switch (mwhich)
                            {
                                case 1:
                                    changetikuanpasswordNpasswordTv1.setText(which + "");
                                    break;
                                case 2:
                                    changetikuanpasswordNpasswordTv2.setText(which + "");
                                    break;
                                case 3:
                                    changetikuanpasswordNpasswordTv3.setText(which + "");
                                    break;
                                case 4:
                                    changetikuanpasswordNpasswordTv4.setText(which + "");
                                    break;
                            }
                        }
                        return true;
                    }
                })
                .show();
    }
}

