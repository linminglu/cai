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
import com.example.admin.caipiao33.bean.QqPingTaiBean;
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


public class OtherPingTaiActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
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
    private String name = "";
    private String topupamount;
    private String payId;
    private QqPingTaiBean qqPingTaiBean;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqpingtai);
        ButterKnife.bind(this);
        name = getIntent().getStringExtra(Constants.EXTRA_TOPUP_NAME);
        topupamount = getIntent().getStringExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT);
        payId = getIntent().getStringExtra(Constants.EXTRA_TOPUP_PAYID);
        toolbar.setTitle(name);
        initData();
        initView();
    }

    private void initData()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("payId", payId);
        map.put("amount", topupamount);

        HttpUtil.requestSecond("user", "rotherScanNext", map, QqPingTaiBean.class, OtherPingTaiActivity.this, new MyResponseListener<QqPingTaiBean>()
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
                qqpingtaiSteps.loadDataWithBaseURL("about:blank", result.getStep(), "text/html", "utf-8", null);
                MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + result.getImg(), qqpingtaiErweima, OtherPingTaiActivity.this);
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
                Intent intent = new Intent(OtherPingTaiActivity.this, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, OtherPingTaiActivity.this, new MyResponseListener<String>()
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
        map.put("orderNo", qqPingTaiBean.getOrderNo());

        HttpUtil.requestSecond("user", "rqqpayScanSubmit", map, String.class, OtherPingTaiActivity.this, new MyResponseListener<String>()
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
        Intent intent = new Intent(OtherPingTaiActivity.this, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        this.toolbar = toolbar;
        toolbar.setTitle(name);
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
        qqpingtaiErweima.setOnLongClickListener(new View.OnLongClickListener()
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


    @Override
    public void onPermissionsGranted(int i, List<String> list)
    {
        ImageUtils.GetandSaveCurrentImage(OtherPingTaiActivity.this);
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list)
    {

    }
}

