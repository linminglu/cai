package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
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
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IZouShiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.ZouShiPresenter;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description : 走势页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class ZouShiFragment extends BaseFragment implements View.OnClickListener, IZouShiContract.View
{
    Unbinder unbinder;
    @BindView(R.id.zoushi_webView)
    WebView zoushiWebView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private boolean isFirst = true;
    private GouCaiBean mGouCaiBean;
    private IZouShiContract.Presenter mPresenter;
    private ArrayList<String> names;
    private boolean isError = false;

    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public ZouShiFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_zoushi, container, false);
        mInflater = inflater;
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        mPresenter = new ZouShiPresenter(this, swipeRefreshLayout);
        mPresenter.loadData();
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
                mLoadingLayout.setOnStartLoading(null);
                mPresenter.loadData();
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
        WebSettings webSettings = zoushiWebView.getSettings();
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
        zoushiWebView.setWebChromeClient(new MyWebChromeClient());
        zoushiWebView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                if (isFirst)
                {
                    showLoadingLayoutError4Ami(swipeRefreshLayout);
                }
                isError = true;
                super.onReceivedError(view, request, error);
            }

            public void onPageFinished(WebView view, String url)
            {
                if (!isError)
                {
                    if (isFirst)
                    {
                        hideLoadingLayout4Ami(swipeRefreshLayout);
                        isFirst = false;
                    }
                }
                isError = false;
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
                mPresenter.refreshData();
            }
        });
    }

    @Override
    public void onClick(final View v)
    {
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.toolbar_title})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.toolbar_title: // 玩法选择
                showOptionsDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public BaseActivity getBaseActivity()
    {
        return mainActivity;
    }

    @Override
    public void showErrorMsg(String msg)
    {
        ToastUtil.show(msg);
    }

    @Override
    public void update(GouCaiBean bean)
    {
        if (bean != null)
        {
            mGouCaiBean = bean;
            zoushiWebView.loadUrl(HttpUtil.mNewUrl + "/api/trend?gid=" + bean.getAll()
                    .get(0)
                    .getNum());
            toolbarTitle.setText(bean.getAll().get(0).getName() + "的走势");
            names = new ArrayList<>();
            for (int i = 0; i < mGouCaiBean.getAll().size(); i++)
            {
                names.add(mGouCaiBean.getAll().get(i).getName());
            }
        }
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

    private void showOptionsDialog()
    {
        if (null == mGouCaiBean && null == names)
        {
            return;
        }

        new MaterialDialog.Builder(mainActivity).title("玩法选择")
                .items(names)
                .positiveText(R.string.dialog_ok)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice()
                {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
                    {
                        if (which != -1)
                        {
                            zoushiWebView.loadUrl(HttpUtil.mNewUrl + "/api/trend?gid=" + mGouCaiBean
                                    .getAll()
                                    .get(which)
                                    .getNum());
                            toolbarTitle.setText(mGouCaiBean.getAll().get(which).getName() + "的走势");
                        }
                        return true;
                    }
                })
                .show();
    }
}

