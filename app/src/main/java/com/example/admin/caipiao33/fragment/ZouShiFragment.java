package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.BuyActivity;
import com.example.admin.caipiao33.BuyRoomActivity;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.TypeBean;
import com.example.admin.caipiao33.contract.IZouShiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.ZouShiPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.fragment_zoushi_title)
    TextView fragmentZoushiTitle;
    @BindView(R.id.fragment_tubuy_btn)
    Button fragmentTubuyBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private boolean isFirst = true;
    private ArrayList<TypeBean> typeBeans;
    private IZouShiContract.Presenter mPresenter;
    private ArrayList<String> names;
    private boolean isError = false;
    private String mNumber;
    private String mTitleStr;
    private String mRoomId;
    private String mPlayId;
    private String mPlayId1;

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
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error)
            {
                if (isFirst)
                {
                    showLoadingLayoutError4Ami(swipeRefreshLayout);
                }
                ToastUtil.show("页面加载失败，请检查您的网络链接是否正确！");
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

    @OnClick({R.id.toolbar_title, R.id.fragment_tubuy_btn})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.toolbar_title: // 玩法选择
                showOptionsDialog();
                break;
            case R.id.fragment_tubuy_btn: // 玩法选择
                mPresenter.requestRoomData(mNumber, mTitleStr);
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
    public void update(ArrayList<TypeBean> beans)
    {
        if (beans != null)
        {
            typeBeans = beans;
            zoushiWebView.loadUrl(HttpUtil.mNewUrl + "/api/trend?gid=" + beans.get(0).getNum());
            mNumber = beans.get(0).getNum();
            mTitleStr = beans.get(0).getName();
            //            toolbarTitle.setText(bean.getAll().get(0).getName() + "的走势");
            fragmentZoushiTitle.setText(beans.get(0).getName());
            names = new ArrayList<>();
            for (int i = 0; i < beans.size(); i++)
            {
                names.add(beans.get(i).getName());
            }
        }
    }

    @Override
    public void toBuyRoom(BuyRoomBean bean, String title)
    {
        /**
         * 两种情况
         * room
         *  -- 显示房间列表，再次选择一项进入page页面
         * page
         *  -- 立即购买页面
         */

        if (bean.getPage().equals("room"))
        {
            Intent intent = new Intent(getActivity(), BuyRoomActivity.class);
            intent.putExtra(Constants.EXTRA_TITLE, title);
            intent.putExtra(Constants.EXTRA_BUY_ROOM_BEAN, bean);
            startActivity(intent);
        }
        else
        {
            String roomId = bean.getRoomId();
            String playId = null;
            String playId1 = null;
            List<BuyRoomBean.PlayListBean> playList = bean.getPlayList();
            if (null != playList && playList.size() > 0)
            {
                BuyRoomBean.PlayListBean playListBean = playList.get(0);
                playId = playListBean.getPlayId();
                playId1 = playListBean.getPlayId1();
            }
            Intent intent = new Intent(mainActivity, BuyActivity.class);
            intent.putExtra(Constants.EXTRA_TITLE, title);
            intent.putExtra(Constants.EXTRA_NUMBER, bean.getNum());
            intent.putExtra(Constants.EXTRA_ROOM_ID, roomId);
            intent.putExtra(Constants.EXTRA_PLAY_ID, playId);
            intent.putExtra(Constants.EXTRA_PLAY_ID1, playId1);
            startActivity(intent);
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
        if (null == typeBeans && null == names)
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
                            zoushiWebView.loadUrl(HttpUtil.mNewUrl + "/api/trend?gid=" + typeBeans

                                    .get(which).getNum());
                            mNumber = typeBeans.get(which).getNum();
                            mTitleStr = typeBeans.get(which).getName();
                            fragmentZoushiTitle.setText(typeBeans.get(which).getName());
                            //                            toolbarTitle.setText(mGouCaiBean.getAll().get(which).getName() + "的走势");
                        }
                        return true;
                    }
                })
                .show();
    }
}

