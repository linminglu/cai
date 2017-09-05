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
import com.example.admin.caipiao33.bean.QqPingTaiBean;
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


public class QqPayPingTaiActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.qqpingtai_dingdanhao)
    TextView qqpingtaiDingdanhao;
    @BindView(R.id.qqpingtai_qqhao)
    TextView qqpingtaiqqhao;
    @BindView(R.id.qqpingtai_qqming)
    TextView qqpingtaiqqming;
    @BindView(R.id.qqpingtai_amount)
    TextView qqpingtaiAmount;
    @BindView(R.id.qqpingtai_tip)
    TextView qqpingtaiTip;
    @BindView(R.id.qqpingtai_erweima)
    ImageView qqpingtaiErweima;
    @BindView(R.id.qqpingtai_errortip)
    TextView qqpingtaiErrortip;
    @BindView(R.id.qqpingtai_shangyibu)
    Button qqpingtaiShangyibu;
    @BindView(R.id.qqpingtai_woyizhifu)
    Button qqpingtaiWoyizhifu;
    @BindView(R.id.qqpingtai_steps)
    WebView qqpingtaiSteps;
    private String topupamount;
    private String payId;
    private QqPingTaiBean qqPingTaiBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqpingtai);
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

        HttpUtil.requestSecond("user", "rqqpayScanNext", map, QqPingTaiBean.class, QqPayPingTaiActivity.this, new MyResponseListener<QqPingTaiBean>()
        {
            @Override
            public void onSuccess(QqPingTaiBean result)
            {
                qqPingTaiBean = result;
                qqpingtaiDingdanhao.setText(result.getOrderNo());
                qqpingtaiqqhao.setText(result.getCode());
                qqpingtaiqqming.setText(result.getName());
                qqpingtaiAmount.setText(result.getAmount());
                qqpingtaiTip.setText(result.getTip());
                qqpingtaiErrortip.setText(result.getErrorTip());
                qqpingtaiSteps.loadDataWithBaseURL("about:blank", result.getSteps(), "text/html", "utf-8", null);
                MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + result.getImg(), qqpingtaiErweima, QqPayPingTaiActivity.this);
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
        map.put("orderNo", qqPingTaiBean.getOrderNo());

        HttpUtil.requestSecond("user", "rqqpayScanSubmit", map, String.class, QqPayPingTaiActivity.this, new MyResponseListener<String>()
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
        toolbar.setTitle(R.string.s_qq_pingtaichongzhi);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        //        tianjiahaoyouTitle.setText(qqPayBean.getPayName());
        //        tianjiahaoyou_Subtitle.setText(qqPayBean.getPayDesc());
        //        tianjiahaoyouName.setText("微信账号：" + qqPayBean.getCode() + "    微信昵称：" + qqPayBean.getName());
        //        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + qqPayBean.getPayImg(), tianjiahaoyouErweima, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @OnClick({R.id.qqpingtai_shangyibu, R.id.qqpingtai_woyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.qqpingtai_shangyibu:
                finish();
                break;
            case R.id.qqpingtai_woyizhifu:
                toNext();
                break;
        }
    }
}

