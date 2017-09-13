package com.example.admin.caipiao33.topupactivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.bean.AliBankBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AliBankActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.alibank_dingdanhao)
    TextView alibankDingdanhao;
    @BindView(R.id.alibank_alihao)
    TextView alibankAlihao;
    @BindView(R.id.alibank_alibanknum)
    TextView alibankAlibanknum;
    @BindView(R.id.alibank_bankname)
    TextView alibankBankname;
    @BindView(R.id.alibank_amount)
    TextView alibankAmount;
    @BindView(R.id.alibank_tip)
    TextView alibankTip;
    @BindView(R.id.alibank_shangyibu)
    Button alibankShangyibu;
    @BindView(R.id.alibank_woyizhifu)
    Button alibankWoyizhifu;
    private String topupamount;
    private String payId;
    private AliBankBean aliBankBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alibank);
        ButterKnife.bind(this);
        topupamount = getIntent().getStringExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT);
        payId = getIntent().getStringExtra(Constants.EXTRA_TOPUP_PAYID);
        initData();
        initView();
    }

    private void initData()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("payId", payId);
        map.put("amount", topupamount);

        HttpUtil.requestSecond("user", "ralipayBankNext", map, AliBankBean.class, AliBankActivity.this, new MyResponseListener<AliBankBean>()
        {
            @Override
            public void onSuccess(AliBankBean result)
            {
                aliBankBean = result;
                alibankDingdanhao.setText(result.getOrderNo());
                alibankAlihao.setText(result.getAccountName());
                alibankAlibanknum.setText(result.getAccountCode());
                alibankBankname.setText(result.getBankName());
                alibankAmount.setText(result.getAmount());
                alibankTip.setText(result.getTip());
                hideLoadingLayout();
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
                showLoadingLayoutError();
            }

            @Override
            public void onFinish()
            {
            }
        }, null);
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_ali_bank);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                initData();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @OnClick({R.id.alibank_shangyibu, R.id.alibank_woyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.alibank_shangyibu:
                finish();
                break;
            case R.id.alibank_woyizhifu:
                ToastUtil.show("提交成功");
                finish();
                break;
        }
    }
}

