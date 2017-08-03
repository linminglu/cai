package com.example.admin.caipiao33;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.admin.caipiao33.utils.Constants;
import com.socks.library.KLog;

/**
 * 网页web
 * 从外部传入Constants.EXTRA_URL  访问的url
 * 从外部传入Constants.EXTRA_TITLE  该页面title
 * Created by lsd on 2016/3/14.
 */
public class WebUrlActivity extends BaseActivity
{

    private WebView webView;
    private ProgressBar mProgressbar;
    private Toolbar mToolbar;
    private String mUrl;
    private String mTitle;
    private View layoutError;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_url);
        setResult(RESULT_CANCELED);

        layoutError = findViewById(R.id.protocol_error_layout);
        findViewById(R.id.protocol_refresh).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                layoutError.setVisibility(View.GONE);
                webView.loadUrl(mUrl);
            }
        });
        mUrl = getIntent().getStringExtra(Constants.EXTRA_URL);
        mTitle = getIntent().getStringExtra(Constants.EXTRA_TITLE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        KLog.e(mUrl);
        webView = (WebView) findViewById(R.id.protocol_webView);
        webView.loadUrl(mUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        mProgressbar = (ProgressBar) findViewById(R.id.protocol_progressbar);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                mProgressbar.setVisibility(View.VISIBLE);
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
        });

        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100)
                {
                    mProgressbar.setVisibility(View.GONE);
                }
                else
                {
                    mProgressbar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    }
}
