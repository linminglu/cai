package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.TiKuanBean;
import com.example.admin.caipiao33.contract.ITiKuanContract;
import com.example.admin.caipiao33.presenter.TiKuanPresenter;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

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
                break;
        }
        return false;
    }

    @Override
    public void updata(TiKuanBean result)
    {
        tikuanNameTv.setText(result.getAccountName());
        tikuanYueTv.setText("¥" + result.getAmount());
        tikuanShifouTv.setText(result.getCanWithdraw());
        tikuanMianfeiTv.setText(result.getFreeTimes());
        tikuanLastRechargeTv.setText("您最后一笔入款" + result.getLastRecharge() + "需下注" + result.getNeedDml() + "才能提款,当前已下注" + result
                .getRealDml());
        tikuanYinhangTv.setText(result.getBankName());
        tikuanZhanghaoTv.setText(result.getAccountCode());
        tikuanTipTv.setText(result.getWithdrawTip());
    }

    @Override
    public void tiKuanOk(String result)
    {
        ToastUtil.show("提款申请成功，请等待后台审核！");
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

