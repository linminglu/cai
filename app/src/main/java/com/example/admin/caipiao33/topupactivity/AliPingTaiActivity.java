package com.example.admin.caipiao33.topupactivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.bean.AliPingTaiBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AliPingTaiActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.alipingtai_dingdanhao)
    TextView alipingtaiDingdanhao;
    @BindView(R.id.alipingtai_alihao)
    TextView alipingtaialihao;
    @BindView(R.id.alipingtai_aliming)
    TextView alipingtaialiming;
    @BindView(R.id.alipingtai_amount)
    TextView alipingtaiAmount;
    @BindView(R.id.alipingtai_tip)
    TextView alipingtaiTip;
    @BindView(R.id.alipingtai_erweima)
    ImageView alipingtaiErweima;
    @BindView(R.id.alipingtai_errortip)
    TextView alipingtaiErrortip;
    @BindView(R.id.alipingtai_shangyibu)
    Button alipingtaiShangyibu;
    @BindView(R.id.alipingtai_woyizhifu)
    Button alipingtaiWoyizhifu;
    @BindView(R.id.alipingtai_steps)
    WebView alipingtaiSteps;
    private String topupamount;
    private String payId;
    private AliPingTaiBean aliPingTaiBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipingtai);
        ButterKnife.bind(this);
        topupamount = getIntent().getStringExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT);
        payId = getIntent().getStringExtra(Constants.EXTRA_TOPUP_PAYID);
        initData();
        initView();
    }

    private void initData()
    {
        showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("payId", payId);
        map.put("amount", topupamount);

        HttpUtil.requestSecond("user", "ralipayScanNext", map, AliPingTaiBean.class, AliPingTaiActivity.this, new MyResponseListener<AliPingTaiBean>()
        {
            @Override
            public void onSuccess(AliPingTaiBean result)
            {
                aliPingTaiBean = result;
                alipingtaiDingdanhao.setText(result.getOrderNo());
                alipingtaialihao.setText(result.getCode());
                alipingtaialiming.setText(result.getName());
                alipingtaiAmount.setText(result.getAmount());
                alipingtaiTip.setText(result.getTip());
                alipingtaiErrortip.setText(result.getErrorTip());
                alipingtaiSteps.loadDataWithBaseURL("about:blank", result.getSteps(), "text/html", "utf-8", null);
                MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + result.getImg(), alipingtaiErweima, AliPingTaiActivity.this);
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

    private void toNext()
    {
        showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("payId", payId);
        map.put("amount", topupamount);
        map.put("orderNo", aliPingTaiBean.getOrderNo());

        HttpUtil.requestSecond("user", "ralipayScanSubmit", map, String.class, AliPingTaiActivity.this, new MyResponseListener<String>()
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

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_ali_pingtaichongzhi);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        //        tianjiahaoyouTitle.setText(aliPayBean.getPayName());
        //        tianjiahaoyou_Subtitle.setText(aliPayBean.getPayDesc());
        //        tianjiahaoyouName.setText("微信账号：" + aliPayBean.getCode() + "    微信昵称：" + aliPayBean.getName());
        //        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + aliPayBean.getPayImg(), tianjiahaoyouErweima, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @OnClick({R.id.alipingtai_shangyibu, R.id.alipingtai_woyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.alipingtai_shangyibu:
                finish();
                break;
            case R.id.alipingtai_woyizhifu:
                toNext();
                break;
        }
    }
}

