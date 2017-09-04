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
import com.example.admin.caipiao33.bean.WeiXinPingTaiBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.LoginEvent;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WeiXinPingTaiActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.weixinpingtai_dingdanhao)
    TextView weixinpingtaiDingdanhao;
    @BindView(R.id.weixinpingtai_weixinhao)
    TextView weixinpingtaiWeixinhao;
    @BindView(R.id.weixinpingtai_weixinming)
    TextView weixinpingtaiWeixinming;
    @BindView(R.id.weixinpingtai_amount)
    TextView weixinpingtaiAmount;
    @BindView(R.id.weixinpingtai_tip)
    TextView weixinpingtaiTip;
    @BindView(R.id.weixinpingtai_erweima)
    ImageView weixinpingtaiErweima;
    @BindView(R.id.weixinpingtai_errortip)
    TextView weixinpingtaiErrortip;
    @BindView(R.id.weixinpingtai_shangyibu)
    Button weixinpingtaiShangyibu;
    @BindView(R.id.weixinpingtai_woyizhifu)
    Button weixinpingtaiWoyizhifu;
    @BindView(R.id.weixinpingtai_steps)
    WebView weixinpingtaiSteps;
    private String topupamount;
    private String payId;
    private WeiXinPingTaiBean weiXinPingTaiBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixinpingtai);
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

        HttpUtil.requestSecond("user", "rwechatScanNext", map, WeiXinPingTaiBean.class, WeiXinPingTaiActivity.this, new MyResponseListener<WeiXinPingTaiBean>()
        {
            @Override
            public void onSuccess(WeiXinPingTaiBean result)
            {
                weiXinPingTaiBean = result;
                weixinpingtaiDingdanhao.setText(result.getOrderNo());
                weixinpingtaiWeixinhao.setText(result.getCode());
                weixinpingtaiWeixinming.setText(result.getName());
                weixinpingtaiAmount.setText(result.getAmount());
                weixinpingtaiTip.setText(result.getTip());
                weixinpingtaiErrortip.setText(result.getErrorTip());
                weixinpingtaiSteps.loadDataWithBaseURL("about:blank", result.getSteps(), "text/html", "utf-8", null);
                MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + result.getImg(), weixinpingtaiErweima, WeiXinPingTaiActivity.this);
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
        map.put("orderNo", weiXinPingTaiBean.getOrderNo());

        HttpUtil.requestSecond("user", "rwechatScanSubmit", map, String.class, WeiXinPingTaiActivity.this, new MyResponseListener<String>()
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
        toolbar.setTitle(R.string.s_weixin_pingtaichongzhi);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        //        tianjiahaoyouTitle.setText(weiXinPayBean.getPayName());
        //        tianjiahaoyou_Subtitle.setText(weiXinPayBean.getPayDesc());
        //        tianjiahaoyouName.setText("微信账号：" + weiXinPayBean.getCode() + "    微信昵称：" + weiXinPayBean.getName());
        //        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + weiXinPayBean.getPayImg(), tianjiahaoyouErweima, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @OnClick({R.id.weixinpingtai_shangyibu, R.id.weixinpingtai_woyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.weixinpingtai_shangyibu:
                finish();
                break;
            case R.id.weixinpingtai_woyizhifu:
                toNext();
                break;
        }
    }
}

