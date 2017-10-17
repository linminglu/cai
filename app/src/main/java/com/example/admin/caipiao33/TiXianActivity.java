package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.SettingBean;
import com.example.admin.caipiao33.bean.TiKuanBean;
import com.example.admin.caipiao33.contract.ITiKuanContract;
import com.example.admin.caipiao33.presenter.TiKuanPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//提款
public class TiXianActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ITiKuanContract.View
{
    @BindView(R.id.tikuan_name_tv)
    TextView tikuanNameTv;
    @BindView(R.id.tikuan_yue_tv)
    TextView tikuanYueTv;
    @BindView(R.id.tikuan_shifou_tv)
    TextView tikuanShifouTv;
    @BindView(R.id.tikuan_mianfei_tv)
    TextView tikuanMianfeiTv;
    @BindView(R.id.tikuan_lastRecharge_tv)
    TextView tikuanLastRechargeTv;
    @BindView(R.id.tikuan_yinhang_tv)
    TextView tikuanYinhangTv;
    @BindView(R.id.tikuan_zhanghao_tv)
    TextView tikuanZhanghaoTv;
    @BindView(R.id.tikuan_jine_et)
    EditText tikuanJineEt;
    @BindView(R.id.tikuan_mima_et)
    EditText tikuanMimaEt;
    @BindView(R.id.tikuan_tip_tv)
    TextView tikuanTipTv;
    @BindView(R.id.tikuan_errortip_tv)
    TextView tikuanErrorTipTv;
    @BindView(R.id.tikuan_submit_btn)
    Button tikuanSubmitBtn;
    private ITiKuanContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tikuan);
        ButterKnife.bind(this);
        mPresenter = new TiKuanPresenter(this, null);
        initView();
        mPresenter.getTiKuan();
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.getTiKuan();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_tixian, menu);
        return true;
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_tikuan);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            //提现记录
            case R.id.action_message:
                Intent intent = new Intent(TiXianActivity.this, TiKuanJiLuActivity.class);
                startActivity(intent);
                break;
        }
        return false;
    }

    @Override
    public void updata(TiKuanBean result)
    {
        if (result.getStatus().equals("-1"))
        {
            mPresenter.getSettingOp();
        }
        else if (result.getStatus().equals("1"))
        {
            hideLoadingLayout();
            tikuanNameTv.setText(result.getAccountName());
            tikuanYueTv.setText("¥" + result.getAmount());
            tikuanShifouTv.setText(result.getCanWithdraw());
            tikuanMianfeiTv.setText(result.getFreeTimes());
            tikuanLastRechargeTv.setText("您最后一笔入款" + result.getLastRecharge() + "需下注" + result.getNeedDml() + "才能提款,当前已下注" + result
                    .getRealDml());
            tikuanYinhangTv.setText(result.getBankName());
            tikuanZhanghaoTv.setText(result.getAccountCode());
            tikuanTipTv.setText(result.getWithdrawTip());
            tikuanErrorTipTv.setVisibility(View.GONE);
        }
        else
        {
            hideLoadingLayout();
            tikuanErrorTipTv.setVisibility(View.VISIBLE);
            tikuanErrorTipTv.setText(result.getCloseTip());
        }
    }

    @Override
    public void tiKuanOk(String result)
    {
        ToastUtil.show("提款申请成功，请等待后台审核！");
        finish();
    }

    @Override
    public void hasmima(SettingBean result)
    {
        if (result.getTkPasswd().equals("0"))
        {
            ToastUtil.show("还未绑定银行卡！请先设置提款密码！");
            Intent intent = new Intent(TiXianActivity.this, ChangeTiKuanPasswordActivity.class);
            intent.putExtra("fromtikuan", 1);
            startActivity(intent);
        }
        else
        {
            ToastUtil.show("还未绑定银行卡，请先绑定银行卡才能提现！");
            Intent intent = new Intent(TiXianActivity.this, ChangeBankActivity.class);
            intent.putExtra(Constants.CHANGE_TIKUAN_PASSWORD, 0);
            startActivity(intent);
        }
        finish();
    }

    @OnClick(R.id.tikuan_submit_btn)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tikuan_submit_btn:
                if (StringUtils.isEmpty2(tikuanJineEt.getText()
                        .toString()) || tikuanJineEt.getText().toString().equals("0"))
                {
                    ToastUtil.show("请输入提款金额！");
                    return;
                }
                if (StringUtils.isEmpty2(tikuanMimaEt.getText().toString()))
                {
                    ToastUtil.show("请输入提款密码！");
                    return;
                }
                mPresenter.submitTiKuan(tikuanJineEt.getText().toString(), tikuanMimaEt.getText()
                        .toString());
                break;
        }
    }
}

