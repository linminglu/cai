package com.example.admin.caipiao33;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.UserConfig;
import com.example.admin.caipiao33.wheelview.ProgressDialogBar;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

public class WebActivity extends Activity
{

    private static final String LOG_TAG = "WebView";

    protected ProgressDialogBar progressDialogBar;

    private Handler mHandler = new Handler();
    private WebView webView;
    private ProgressBar mProgressbar;
    private Toolbar mToolbar;
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


        webSettings.setUserAgentString("Android_Lottery");

        webView.setWebChromeClient(new MyWebChromeClient());

        //        webView.addJavascriptInterface(new JavaScriptInterface(), "app");

        mProgressbar = (ProgressBar) findViewById(R.id.protocol_progressbar);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                showLoadingDialog(true);
                //                mProgressbar.setVisibility(View.VISIBLE);
                mUrl = url;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
                layoutError.setVisibility(View.VISIBLE);
            }


            public void onPageFinished(WebView view, String url)
            {
                CookieManager cookieManager = CookieManager.getInstance();
                String CookieStr = cookieManager.getCookie(url);
                if (!StringUtils.isEmpty2(CookieStr))
                {
                    String CookieStr1 = CookieStr.substring(CookieStr.indexOf("token=") + 6, CookieStr
                            .length());
                    UserConfig.getInstance().save(WebActivity.this, CookieStr1);
                }
                KLog.d(LOG_TAG, url);
                if (url.contains("#_TITLE_#"))
                {
                    anndetials_headlayout.setVisibility(View.VISIBLE);
                    int index = url.indexOf("#_TITLE_#");
                    String right = url.substring(index + 9);
                    agreement_web_title.setText(right);
                }
                else
                {
                    anndetials_headlayout.setVisibility(View.GONE);
                }
                super.onPageFinished(view, url);
            }

        });
        getHomePageUrl();
    }

    private void getHomePageUrl()
    {
        HttpUtil.post(null, null, String.class, WebActivity.this, new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                KLog.d(LOG_TAG, result);
                mUrl = result;
                if (isFirst)
                {
                    if (StringUtils.isEmpty2(UserConfig.getInstance()
                            .getTokenString(WebActivity.this)))
                    {
                        webView.loadUrl(mUrl);
                        KLog.d(LOG_TAG, mUrl);
                    }
                    else
                    {
                        TreeMap<String, String> map = new TreeMap<>();
                        map.put("data", UserConfig.getInstance().getTokenString(WebActivity.this));
                        showLoadingDialog(true);
                        HttpUtil.post(map, null, String.class, WebActivity.this, new MyResponseListener<String>()
                        {
                            @Override
                            public void onSuccess(String result)
                            {
                                KLog.d(LOG_TAG, result);
                            }

                            @Override
                            public void onFailed(int code, String msg)
                            {
                                KLog.d(LOG_TAG, msg);
                            }

                            @Override
                            public void onFinish()
                            {
                                webView.loadUrl(mUrl);
                            }
                        }, null);
                    }
                }
                else
                {
                    TreeMap<String, String> map = new TreeMap<>();
                    map.put("data", UserConfig.getInstance().getTokenString(WebActivity.this));
                    showLoadingDialog(true);
                    HttpUtil.post(map, null, String.class, WebActivity.this, new MyResponseListener<String>()
                    {
                        @Override
                        public void onSuccess(String result)
                        {
                            KLog.d(LOG_TAG, result);
                        }

                        @Override
                        public void onFailed(int code, String msg)
                        {
                            KLog.d(LOG_TAG, msg);
                        }

                        @Override
                        public void onFinish()
                        {
                            hideLoadingDialog();
                        }
                    }, null);
                }
            }

            @Override
            public void onFailed(int code, String msg)
            {
                KLog.d(LOG_TAG, msg);
            }

            @Override
            public void onFinish()
            {
                webView.loadUrl(mUrl);
            }
        }, null);
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
