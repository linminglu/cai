package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;


public class SplashActivity extends BaseActivity
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
        requestBaseUrl();
        handler.postDelayed(initRunnbale, 3000);
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
                isLoadSuccess = true;
                MyApplication.getInstance().setBaseUrlBean(result);
                HttpUtil.changeBaseUrl(result.getUrl());
                if (isDelayTimeOver) {
                    toMainActivity();
                }
            }

            @Override
            public void onFailed(int code, String msg)
            {
                // 提示网络问题，重试
                showRetryDialog();
            }

            @Override
            public void onFinish()
            {

            }
        });
    }

    private void showRetryDialog() {
        new MaterialDialog.Builder(this)
                .content("当前访问网络有问题，请检查重试")
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
            if (isLoadSuccess) {
                toMainActivity();
            }
        }
    };

    private void toMainActivity()
    {
        if (isJumpMain) {
            return;
        }
        isJumpMain = true;
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
        finish();
    }
}

