package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.caipiao33.utils.Constants;
import com.socks.library.KLog;

/**
 * 网页web
 * 从外部传入Constants.EXTRA_URL  访问的url
 * 从外部传入Constants.EXTRA_TITLE  该页面title
 * Created by lsd on 2016/3/14.
 */
public class KaiJiangUrlActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{

    private WebView webView;
    private ProgressBar mProgressbar;
    private Toolbar mToolbar;
    private String mUrl;
    private String mTitle;
    private View layoutError;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mTitle = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        setContentView(R.layout.activity_kaijiangjieguo_url);
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

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(mTitle);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_kaijiangjieguo, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_jintian: // 今天
                mUrl = getIntent().getStringExtra(Constants.EXTRA_URL);
                webView.loadUrl(mUrl);
                break;
            case R.id.action_zuotian: // 昨天
                mUrl = getIntent().getStringExtra(Constants.EXTRA_URL);
                webView.loadUrl(mUrl);
                break;
            case R.id.action_qiantian: // 前天
                mUrl = getIntent().getStringExtra(Constants.EXTRA_URL);
                webView.loadUrl(mUrl);
                break;
            default:
                break;
        }
        return false;
    }
}
