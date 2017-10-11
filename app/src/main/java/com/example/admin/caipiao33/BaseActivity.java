package com.example.admin.caipiao33;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.utils.AppUtils;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.wheelview.ProgressDialogBar;

/**
 * 所有Activity的基类
 * 实现了显示隐藏数据加载中Dialog
 * 实现了首次加载数据显示隐藏LoadingLayout
 * 需要控制页面view的显示隐藏，需要重写getContentView，把页面的view返回给我
 */
public class BaseActivity extends AppCompatActivity implements IBaseView
{

    protected ProgressDialogBar progressDialogBar;
    protected LoadingLayout mLoadingLayout;
    private boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        IntentFilter updatefilter = new IntentFilter();
        updatefilter.addAction(Constants.UPDATEACTION);
        registerReceiver(updatebroadcast, updatefilter);
        super.onCreate(savedInstanceState);
    }

    public void showDialog(String msg, MaterialDialog.SingleButtonCallback listener, MaterialDialog.SingleButtonCallback nagativeListener)
    {
        MaterialDialog.Builder builder1 = new MaterialDialog.Builder(this);
        builder1.title(R.string.dialog_tips)
                .content(msg)
                .positiveText(R.string.dialog_ok)
                .onPositive(listener)
                .negativeText(R.string.dialog_cancel)
                .onNegative(nagativeListener)
                .cancelable(false)
                .show();
    }

    @Override
    public BaseActivity getBaseActivity()
    {
        return this;
    }

    @Override
    public void showLoadingDialog(boolean isCanceledOnTouchOutside)
    {
        if (progressDialogBar == null)
        {
            progressDialogBar = ProgressDialogBar.createDialog(this);
        }
        progressDialogBar.setProgressMsg(getString(R.string.requesting));
        progressDialogBar.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        progressDialogBar.show();
    }

    @Override
    public void showLoadingDialog()
    {
        showLoadingDialog(false);
    }

    @Override
    public void hideLoadingDialog()
    {
        if (progressDialogBar != null)
        {
            progressDialogBar.dismiss();
        }
    }

    @Override
    public void showLoadingLayout()
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStartLoading(null);
        }
    }

    @Override
    public void hideLoadingLayout()
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStopLoading(this, null);
        }
    }

    @Override
    public void showLoadingLayoutError()
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnLoadingError(this, null);
        }
    }

    @Override
    public void showLoadingLayout4Ami(View view)
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStartLoading(view);
        }
    }

    @Override
    public void hideLoadingLayout4Ami(View view)
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStopLoading(this, view);
        }
    }

    @Override
    public void showLoadingLayoutError4Ami(View view)
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnLoadingError(this, view);
        }
    }

    @Override
    public void showErrorMsg(String msg)
    {

    }

    @Override
    protected void onResume()
    {
        isShow = true;
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        isShow = false;
        super.onPause();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(updatebroadcast);
    }

    private BroadcastReceiver updatebroadcast = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, final Intent intent)
        {
            if (isShow)
            {
                BaseUrlBean baseUrlBean = MyApplication.getInstance().getBaseUrlBean();
                int intCurrVersion = Integer.valueOf(baseUrlBean.getCurrentVersion());
                int intLowVersion = Integer.valueOf(baseUrlBean.getLowVersion());
                int appVersionCode = AppUtils.getAppVersionCode(BaseActivity.this);
                boolean isNormal = false;
                if (appVersionCode < intLowVersion)
                {
                    // 强制更新
                    isNormal = false;
                }
                else
                {
                    // 普通更新
                    isNormal = true;
                }
                final boolean finalNormal = isNormal;
                new MaterialDialog.Builder(BaseActivity.this).title(R.string.update)
                        .content(R.string.download_success)
                        .positiveText(R.string.dialog_ok)
                        .negativeText(R.string.dialog_cancel)
                        .cancelable(isNormal)
                        .onPositive(new MaterialDialog.SingleButtonCallback()
                        {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which)
                            {
                                Intent i = new Intent();
                                i.setAction(Intent.ACTION_VIEW);
                                i.setDataAndType((Uri) intent.getParcelableExtra("fileuri"), "application/vnd.android.package-archive");
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                                startActivity(i);
                                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                                        .cancel(0);
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback()
                        {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                            {
                                // 强制更新
                                if (!finalNormal)
                                {
                                    android.os.Process.killProcess(android.os.Process.myPid());
                                }
                            }
                        })
                        .show();
            }
        }
    };

}
