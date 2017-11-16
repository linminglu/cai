package com.example.admin.caipiao33;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;

import java.util.List;


public class SplashActivity extends BaseActivity implements FcPermissionsCallbacks
{
    private Handler handler = new Handler();
    private ImageView mImageView;
    private boolean isLoadSuccess = false;
    private boolean isDelayTimeOver = false;
    private boolean isJumpMain = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageView = (ImageView) findViewById(R.id.splash_iv);

        mImageView.setVisibility(View.VISIBLE);

        requestCameraPermission();
    }

    private void requestBaseUrl()
    {
        HttpUtil.reConfirmUrl(this, new MyResponseListener<BaseUrlBean>()
        {
            @Override
            public void onSuccess(BaseUrlBean result)
            {
                if (null == result)
                {
                    return;
                }
                String url = result.getUrl();
                if (StringUtils.isEmpty(url))
                {
                    showRetryDialog("");
                    return;
                }
                isLoadSuccess = true;
                MyApplication.getInstance().setBaseUrlBean(result);
                HttpUtil.changeBaseUrl(url);
                if (isDelayTimeOver)
                {
                    toMainActivity();
                }
            }

            @Override
            public void onFailed(int code, String msg)
            {
                // 提示网络问题，重试
                showRetryDialog(msg);
            }

            @Override
            public void onFinish()
            {

            }
        });
    }

    private void showRetryDialog(String msg)
    {
        new MaterialDialog.Builder(this).content(StringUtils.isEmpty2(msg) ? "当前访问网络有问题，请检查重试" : msg)
                .positiveText("重试")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        requestBaseUrl();
                    }
                })
                .negativeText("退出程序")
                .onNegative(new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed()
    {
        //不允许退出
    }

    private Runnable initRunnbale = new Runnable()
    {

        @Override
        public void run()
        {
            isDelayTimeOver = true;
            if (isLoadSuccess)
            {
                toMainActivity();
            }
        }
    };

    private void toMainActivity()
    {
        if (isJumpMain)
        {
            return;
        }
        isJumpMain = true;
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        requestBaseUrl();
        handler.postDelayed(initRunnbale, 3000);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        ToastUtil.show("没有授权权限，部分功能无法正常使用！");
        requestBaseUrl();
        handler.postDelayed(initRunnbale, 3000);
    }
}

