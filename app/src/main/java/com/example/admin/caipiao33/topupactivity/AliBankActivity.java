package com.example.admin.caipiao33.topupactivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.ChongZhiJiLuActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.AliBankBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;
import com.example.admin.caipiao33.views.LoadingLayout;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.alibank_name_et)
    EditText alibankNameEt;
    private String topupamount;
    private String payId;
    private AliBankBean aliBankBean;
    private ClipboardManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alibank);
        ButterKnife.bind(this);
        topupamount = getIntent().getStringExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT);
        payId = getIntent().getStringExtra(Constants.EXTRA_TOPUP_PAYID);
        cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_topup, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_jilu: // 充值记录.
                Intent intent = new Intent(AliBankActivity.this, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, AliBankActivity.this, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        if (result.contains("#_WEBVIEW_#"))
                        {
                            result = result.replaceAll("#_WEBVIEW_#", "");
                            final Uri uri = Uri.parse(result);
                            final Intent it = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(it);
                        }
                        else
                        {
                            toWebUrlActivity(result, "在线客服");
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg)
                    {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish()
                    {
                        hideLoadingDialog();
                    }
                }, null);
                break;
            default:
                break;
        }
        return false;
    }

    // 跳转到网页
    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(AliBankActivity.this, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
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

    @OnClick({R.id.alibank_alihao, R.id.alibank_alibanknum, R.id.alibank_bankname, R.id.alibank_shangyibu, R.id.alibank_woyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.alibank_alihao:
                cm.setText(alibankAlihao.getText());
                ToastUtil.show("开户人已经复制到剪切板");
                break;
            case R.id.alibank_alibanknum:
                cm.setText(alibankAlibanknum.getText());
                ToastUtil.show("银行账号已经复制到剪切板");
                break;
            case R.id.alibank_bankname:
                cm.setText(alibankBankname.getText());
                ToastUtil.show("银行名称已经复制到剪切板");
                break;
            case R.id.alibank_shangyibu:
                finish();
                break;
            case R.id.alibank_woyizhifu:
                toNext();
                break;
        }
    }

    private void toNext()
    {
        showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("payId", payId);
        map.put("amount", topupamount);
        map.put("orderNo", aliBankBean.getOrderNo());
        if (!StringUtils.isEmpty2(alibankNameEt.getText().toString()))
        {
            map.put("inBank", alibankNameEt.getText().toString());
        }

        HttpUtil.requestSecond("user", "ralipayBankSubmit", map, String.class, AliBankActivity.this, new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                ToastUtil.show("提交成功，请稍后查询充值记录！");
                finish();
                EventBus.getDefault().post(new TopupEvent(""));
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
                hideLoadingDialog();
            }
        }, null);
    }
}

