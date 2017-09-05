package com.example.admin.caipiao33.topupactivity;

import android.graphics.Bitmap;
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
import com.example.admin.caipiao33.bean.Qq3SaoMaBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;
import com.example.admin.caipiao33.utils.ZXingUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//微信第三方扫码
public class QqPay3SaoMaActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.qq3saomadingdanhao)
    TextView qq3saomadingdanhao;
    @BindView(R.id.qq3saomaamount)
    TextView qq3saomaamount;
    @BindView(R.id.qq3saomaerweima)
    ImageView qq3saomaerweima;
    @BindView(R.id.qq3saomaerrortip)
    TextView qq3saomaerrortip;
    @BindView(R.id.qq3saomashangyibu)
    Button qq3saomashangyibu;
    @BindView(R.id.qq3saomawoyizhifu)
    Button qq3saomawoyizhifu;
    @BindView(R.id.qq3saomasteps)
    WebView qq3saomasteps;
    private String topupamount;
    private String payId;
    private Qq3SaoMaBean qq3SaoMaBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq3saoma);
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

        HttpUtil.requestThird("user", "recharge", "qqpayNext", map, Qq3SaoMaBean.class, QqPay3SaoMaActivity.this, new MyResponseListener<Qq3SaoMaBean>()
        {
            @Override
            public void onSuccess(Qq3SaoMaBean result)
            {
                qq3SaoMaBean = result;
                qq3saomadingdanhao.setText(result.getOrderNo());
                qq3saomaamount.setText(result.getAmount());
                qq3saomaerrortip.setText(result.getErrorTip());
                //                qq3saomaerrortip.loadDataWithBaseURL("about:blank", result.getErrorTip(), "text/html", "utf-8", null);
                qq3saomasteps.loadDataWithBaseURL("about:blank", result.getSteps(), "text/html", "utf-8", null);
                if (result.getNeedDown().equals("0"))
                {
                    Bitmap bitmap = ZXingUtils.createQRImage(result.getPayUrl(), qq3saomaerweima.getWidth(), qq3saomaerweima
                            .getHeight());
                    qq3saomaerweima.setImageBitmap(bitmap);
                }
                else
                {
                    MyImageLoader.displayImage(result.getPayUrl(), qq3saomaerweima, QqPay3SaoMaActivity.this);
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

    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }


    @OnClick({R.id.qq3saomashangyibu, R.id.qq3saomawoyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.qq3saomashangyibu:
                finish();
                break;
            case R.id.qq3saomawoyizhifu:
                ToastUtil.show("提交成功，请稍后查询充值记录！");
                finish();
                EventBus.getDefault().post(new TopupEvent(""));
                break;
        }
    }
}

