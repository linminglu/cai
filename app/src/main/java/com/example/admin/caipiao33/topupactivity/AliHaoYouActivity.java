package com.example.admin.caipiao33.topupactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.ChongZhiJiLuActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.AliPayBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ImageUtils;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AliHaoYouActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.alihaoyou_title)
    TextView alihaoyouTitle;
    @BindView(R.id.alihaoyou_subtitle)
    TextView alihaoyou_Subtitle;
    @BindView(R.id.alihaoyou_name)
    TextView alihaoyouName;
    @BindView(R.id.alihaoyou_erweima)
    ImageView alihaoyouErweima;
    private AliPayBean aliPayBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alihaoyou);
        ButterKnife.bind(this);
        aliPayBean = (AliPayBean) getIntent().getSerializableExtra(Constants.EXTRA_TOPUP_WEIXIN);
        initView();
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_ali_tianjiahaoyou);
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
                Intent intent = new Intent(AliHaoYouActivity.this, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, AliHaoYouActivity.this, new MyResponseListener<String>()
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
        Intent intent = new Intent(AliHaoYouActivity.this, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }

    private void initView()
    {
        alihaoyouTitle.setText(aliPayBean.getPayName());
        alihaoyou_Subtitle.setText(aliPayBean.getPayDesc());
        alihaoyouName.setText("支付宝账号：" + aliPayBean.getCode() + "    支付宝昵称：" + aliPayBean.getName());
        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + aliPayBean.getPayImg(), alihaoyouErweima, this);

        alihaoyouErweima.setOnLongClickListener(new View.OnLongClickListener()
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

    @Override
    public void onPermissionsGranted(int i, List<String> list)
    {
        ImageUtils.GetandSaveCurrentImage(AliHaoYouActivity.this);
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list)
    {

    }

}

