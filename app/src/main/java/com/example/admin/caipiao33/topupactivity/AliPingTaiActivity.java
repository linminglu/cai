package com.example.admin.caipiao33.topupactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.ChongZhiJiLuActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.AliPingTaiBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ImageUtils;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;
import com.example.admin.caipiao33.views.LoadingLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

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
                Intent intent = new Intent(AliPingTaiActivity.this, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, AliPingTaiActivity.this, new MyResponseListener<String>()
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

    // 跳转到网页
    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(AliPingTaiActivity.this, WebUrlActivity.class);
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
        //        tianjiahaoyouTitle.setText(aliPayBean.getPayName());
        //        tianjiahaoyou_Subtitle.setText(aliPayBean.getPayDesc());
        //        tianjiahaoyouName.setText("微信账号：" + aliPayBean.getCode() + "    微信昵称：" + aliPayBean.getName());
        //        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + aliPayBean.getPayImg(), tianjiahaoyouErweima, this);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                initData();
            }
        });
        alipingtaiErweima.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                showDialog("是否要保存当前页面图片到相册中？", new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        requestCameraPermission();
                    }
                }, new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
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

    @Override
    public void onPermissionsGranted(int i, List<String> list)
    {
        ImageUtils.GetandSaveCurrentImage(AliPingTaiActivity.this);
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list)
    {

    }
}

