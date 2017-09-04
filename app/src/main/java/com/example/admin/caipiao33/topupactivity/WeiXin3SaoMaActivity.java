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
import com.example.admin.caipiao33.bean.WeiXin3SaoMaBean;
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

//微信第三方扫码
public class WeiXin3SaoMaActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.weixin3saomadingdanhao)
    TextView weixin3saomadingdanhao;
    @BindView(R.id.weixin3saomaamount)
    TextView weixin3saomaamount;
    @BindView(R.id.weixin3saomaerweima)
    ImageView weixin3saomaerweima;
    @BindView(R.id.weixin3saomaerrortip)
    TextView weixin3saomaerrortip;
    @BindView(R.id.weixin3saomashangyibu)
    Button weixin3saomashangyibu;
    @BindView(R.id.weixin3saomawoyizhifu)
    Button weixin3saomawoyizhifu;
    @BindView(R.id.weixin3saomasteps)
    WebView weixin3saomasteps;
    private String topupamount;
    private String payId;
    private WeiXin3SaoMaBean weiXin3SaoMaBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin3saoma);
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
        map.put("baseUrl", HttpUtil.mNewUrl);

        HttpUtil.requestSecond("user", "rwechatNext", map, WeiXin3SaoMaBean.class, WeiXin3SaoMaActivity.this, new MyResponseListener<WeiXin3SaoMaBean>()
        {
            @Override
            public void onSuccess(WeiXin3SaoMaBean result)
            {
                weiXin3SaoMaBean = result;
                weixin3saomadingdanhao.setText(result.getOrderNo());
                weixin3saomaamount.setText(result.getAmount());
                weixin3saomaerrortip.setText(result.getErrorTip());
                weixin3saomasteps.loadDataWithBaseURL("about:blank", result.getSteps(), "text/html", "utf-8", null);
                MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + result.getPayUrl(), weixin3saomaerweima, WeiXin3SaoMaActivity.this);
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
        ToastUtil.show("提交成功，请稍后查询充值记录！");
        finish();
        EventBus.getDefault().post(new TopupEvent(""));
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

    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }


    @OnClick({R.id.weixin3saomashangyibu, R.id.weixin3saomawoyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.weixin3saomashangyibu:
                finish();
                break;
            case R.id.weixin3saomawoyizhifu:
                ToastUtil.show("提交成功，请稍后查询充值记录！");
                finish();
                EventBus.getDefault().post(new TopupEvent(""));
                break;
        }
    }
}

