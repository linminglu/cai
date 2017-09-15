package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.KaiJiangUrlActivity;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.KaiJiangDTBean;
import com.example.admin.caipiao33.contract.IKaiJiangContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.utils.CaiZhongUtils;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.admin.caipiao33.R.id.toolbar;

/**
 * Description : 开奖页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class KaiJiangFragment extends BaseFragment implements View.OnClickListener, IKaiJiangContract.View
{
    Unbinder unbinder;
    @BindView(R.id.kaijiang_webView)
    WebView kaijiangWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private boolean isFirst = true;

    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public KaiJiangFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_kaijiang, container, false);
        mInflater = inflater;
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        return parentView;
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) parentView.findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                kaijiangWebView.loadUrl(HttpUtil.mNewUrl + "/api/draw1");
            }
        });
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(mainActivity, null, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0);
        Drawable drawable = a.getDrawable(android.support.v7.appcompat.R.styleable.ActionBar_homeAsUpIndicator);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainActivity.tabSwitchCenter(HomePageFragment.class);
            }
        });
        WebSettings webSettings = kaijiangWebView.getSettings();
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

        kaijiangWebView.loadUrl(HttpUtil.mNewUrl + "/api/draw1");
        kaijiangWebView.setWebChromeClient(new MyWebChromeClient());
        kaijiangWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                Intent intent = new Intent(getActivity(), KaiJiangUrlActivity.class);
                intent.putExtra(Constants.EXTRA_URL, url);
                int pid = 9999;
                try
                {
                    pid = Integer.valueOf(url.substring(url.indexOf("=") + 1));
                }
                catch (Exception e)
                {

                }
                intent.putExtra(Constants.EXTRA_TITLE, CaiZhongUtils.getCaiZhong(pid));
                startActivity(intent);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                if (isFirst)
                {
                    showLoadingLayoutError4Ami(swipeRefreshLayout);
                }
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


            public void onPageFinished(WebView view, String url)
            {
                if (isFirst)
                {
                    hideLoadingLayout4Ami(swipeRefreshLayout);
                    isFirst = false;
                }
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

        });

        //
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                kaijiangWebView.loadUrl(HttpUtil.mNewUrl + "/api/draw1");
            }
        });
    }

    @Override
    public void onClick(final View v)
    {
    }

    @Override
    public BaseActivity getBaseActivity()
    {
        return null;
    }

    @Override
    public void showErrorMsg(String msg)
    {
        ToastUtil.show(msg);
    }

    @Override
    public void update(ArrayList<KaiJiangDTBean> bean)
    {
        //        if (kaiJiangAdapter == null)
        //        {
        //            kaiJiangAdapter = new KaiJiangAdapter(bean, mInflater, kaijiangLv, mainActivity);
        //        }
        //        else
        //        {
        //            kaiJiangAdapter.setBeanContents(bean);
        //        }
        //        kaiJiangAdapter.notifyDataSetChanged();
        //        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * debugging your javascript.  ----用于调试JavaScript
     */
    final class MyWebChromeClient extends WebChromeClient
    {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result)
        {
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
        }

        @Override
        public void onReceivedTitle(WebView view, String title)
        {
            super.onReceivedTitle(view, title);
        }
    }
}

