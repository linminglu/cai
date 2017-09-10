package com.example.admin.caipiao33;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.wheelview.ProgressDialogBar;
import com.socks.library.KLog;

//关于我们
public class AboutActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{

    private static final String LOG_TAG = "WebView";

    protected ProgressDialogBar progressDialogBar;

    private Handler mHandler = new Handler();
    private WebView webView;
    private ProgressBar mProgressbar;
    private String mUrl;
    private View layoutError;
    private TextView agreement_back_tv;
    private TextView agreement_web_title;
    private RelativeLayout anndetials_headlayout;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        setResult(RESULT_CANCELED);

        layoutError = findViewById(R.id.protocol_error_layout);
        agreement_back_tv = (TextView) findViewById(R.id.agreement_back_tv);
        agreement_web_title = (TextView) findViewById(R.id.agreement_web_title);
        anndetials_headlayout = (RelativeLayout) findViewById(R.id.anndetials_headlayout);
        agreement_back_tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (webView.canGoBack())
                {
                    webView.goBack();// 返回前一个页面
                }
            }
        });
        findViewById(R.id.protocol_error_layout).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                layoutError.setVisibility(View.GONE);
                webView.loadUrl(mUrl);
            }
        });

        showLoadingDialog(true);

        webView = (WebView) findViewById(R.id.protocol_webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);

        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        getHomePageUrl();
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.about);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getHomePageUrl()
    {
        HttpUtil.requestSecond("setting", "aboutUs", null, String.class, AboutActivity.this, new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                webView.loadDataWithBaseURL("about:blank", result, "text/html", "utf-8", null);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                KLog.d(LOG_TAG, msg);
            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }


    //    final class JavaScriptInterface

    //    {
    //
    //        JavaScriptInterface()
    //        {
    //        }

    /**
     * 方法必须添加JavascriptInterface注解才可以生效，不然无效果
     * <p>
     * This is not called on the UI thread. Post a runnable to invoke
     * loadUrl on the UI thread.//android调用html的方法
     * <p>
     * This is not called on the UI thread. Post a runnable to invoke
     * loadUrl on the UI thread.//android调用html的方法
     */
    //        @JavascriptInterface
    //        public void toProductDetail(String product_id)
    //        {
    //            KLog.e(product_id+"product_id");
    //            if(!StringUtils.isEmpty(product_id))
    //            {
    //                Intent intent = new Intent(WebUrlActivity.this, ProductDetailActivity.class);
    //                intent.putExtra(Constants.PRODUCT_ID, product_id);
    //                startActivity(intent);
    //            }
    //            else
    //            {
    //                ToastUtil.show("商品ID为空");
    //            }
    //        }

    /**
     * This is not called on the UI thread. Post a runnable to invoke
     * loadUrl on the UI thread.//android调用html的方法
     */
    //        @JavascriptInterface
    //        public void clickOnAndroid()
    //        {
    //            mHandler.post(new Runnable()
    //            {
    //                public void run()
    //                {
    //                    webView.loadUrl("javascript:wave()");
    //                }
    //            });
    //
    //        }
    //    }


    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * debugging your javascript.  ----用于调试JavaScript
     */
    final class MyWebChromeClient extends WebChromeClient
    {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result)
        {
            KLog.d(LOG_TAG, message);
            result.confirm();
            return true;
        }

        /**
         * 重定向网址协议
         * 在网站后面拼接 data=加密后的数据
         * 加密前：error_code=3003 加密使用我们定义好的AES加密
         */
        @Override

        public void onProgressChanged(WebView view, int newProgress)
        {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100)
            {
                hideLoadingDialog();
                //                mProgressbar.setVisibility(View.GONE);
            }
            //            else
            //            {
            //                mProgressbar.setProgress(newProgress);
            //            }
            isFirst = false;
        }

        @Override
        public void onReceivedTitle(WebView view, String title)
        {
            super.onReceivedTitle(view, title);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == Constants.NOLOGIN2START && resultCode == Activity.RESULT_OK)
        {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack())
        {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


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

    public void hideLoadingDialog()
    {
        if (progressDialogBar != null)
        {
            progressDialogBar.dismiss();
        }
    }

}
