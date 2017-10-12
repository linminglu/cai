package com.example.admin.caipiao33.topupactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.ChongZhiJiLuActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.Ali3SaoMaBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;
import com.example.admin.caipiao33.utils.ZXingUtils;
import com.example.admin.caipiao33.views.LoadingLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//微信第三方扫码
public class Ali3SaoMaActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.ali3saomadingdanhao)
    TextView ali3saomadingdanhao;
    @BindView(R.id.ali3saomaamount)
    TextView ali3saomaamount;
    @BindView(R.id.ali3saomaerweima)
    ImageView ali3saomaerweima;
    @BindView(R.id.ali3saomaerrortip)
    TextView ali3saomaerrortip;
    @BindView(R.id.ali3saomashangyibu)
    Button ali3saomashangyibu;
    @BindView(R.id.ali3saomawoyizhifu)
    Button ali3saomawoyizhifu;
    @BindView(R.id.ali3saomasteps)
    WebView ali3saomasteps;
    private String topupamount;
    private String payId;
    private Ali3SaoMaBean ali3SaoMaBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali3saoma);
        ButterKnife.bind(this);
        topupamount = getIntent().getStringExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT);
        payId = getIntent().getStringExtra(Constants.EXTRA_TOPUP_PAYID);
        initData();
        initView();
    }

    private void initData()
    {
        //        showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("payId", payId);
        map.put("amount", topupamount);
        map.put("baseUrl", HttpUtil.mNewUrl);

        HttpUtil.requestThird("user", "recharge", "alipayNext", map, Ali3SaoMaBean.class, Ali3SaoMaActivity.this, new MyResponseListener<Ali3SaoMaBean>()
        {
            @Override
            public void onSuccess(Ali3SaoMaBean result)
            {
                ali3SaoMaBean = result;
                ali3saomadingdanhao.setText(result.getOrderNo());
                ali3saomaamount.setText(result.getAmount());
                ali3saomaerrortip.setText(result.getErrorTip());
                //                ali3saomaerrortip.loadDataWithBaseURL("about:blank", result.getErrorTip(), "text/html", "utf-8", null);
                ali3saomasteps.loadDataWithBaseURL("about:blank", result.getSteps(), "text/html", "utf-8", null);
                if (result.getNeedDown().equals("0"))
                {
                    Bitmap bitmap = ZXingUtils.createQRImage(result.getPayUrl(), ali3saomaerweima.getWidth(), ali3saomaerweima
                            .getHeight());
                    ali3saomaerweima.setImageBitmap(bitmap);
                }
                else if (result.getNeedDown().equals("3"))
                {
                    final Uri uri = Uri.parse(result.getPayUrl());
                    final Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                    finish();
                }
                else
                {
                    MyImageLoader.displayImage(result.getPayUrl(), ali3saomaerweima, Ali3SaoMaActivity.this);
                }
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
                //                hideLoadingDialog();
            }
        }, null);
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
                Intent intent = new Intent(Ali3SaoMaActivity.this, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, Ali3SaoMaActivity.this, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        toWebUrlActivity(result, "在线客服");
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
        Intent intent = new Intent(Ali3SaoMaActivity.this, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
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


    @OnClick({R.id.ali3saomashangyibu, R.id.ali3saomawoyizhifu})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.ali3saomashangyibu:
                finish();
                break;
            case R.id.ali3saomawoyizhifu:
                ToastUtil.show("提交成功，请稍后查询充值记录！");
                finish();
                EventBus.getDefault().post(new TopupEvent(""));
                break;
        }
    }
}

