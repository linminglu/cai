package com.example.admin.caipiao33;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.admin.caipiao33.bean.BaseUrlBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.wheelview.ProgressDialogBar;

/**
 * Created by lsd on 2016/4/1.
 */
public class BaseFragment extends Fragment
{

    protected ProgressDialogBar progressDialogBar;
    protected LoadingLayout mLoadingLayout;

    /**
     * 显示加载中的Dialog，参数isCanceledOnTouchOutside能不能点击外部区域，默认为false
     *
     * @param isCanceledOnTouchOutside
     */
    public void showLoadingDialog(boolean isCanceledOnTouchOutside)
    {
        if (progressDialogBar == null)
        {
            progressDialogBar = ProgressDialogBar.createDialog(getActivity());
        }
        progressDialogBar.setProgressMsg(getString(R.string.requesting));
        progressDialogBar.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        progressDialogBar.show();
    }

    /**
     * 显示加载中的Dialog
     *
     * @deprecated Use {@link #showLoadingDialog(boolean)}
     */
    public void showLoadingDialog()
    {
        showLoadingDialog(false);
    }

    public void hideLoadingDialog()
    {
        if (progressDialogBar != null)
        {
            progressDialogBar.dismiss();
        }
    }

    public void showLoadingLayout()
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStartLoading(null);
        }
    }

    public void hideLoadingLayout()
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStopLoading(getActivity(), null);
        }
    }

    public void showLoadingLayoutError()
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnLoadingError(getActivity(), null);
        }
    }

    public void showLoadingLayout4Ami(View view)
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStartLoading(view);
        }
    }

    public void hideLoadingLayout4Ami(View view)
    {
        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnStopLoading(getActivity(), view);
        }
    }

    public void showLoadingLayoutError4Ami(View view)
    {

        if (null != mLoadingLayout)
        {
            mLoadingLayout.setOnLoadingError(getActivity(), view);
        }
    }

    /**
     * 重新确认baseUrl
     */
    public void reconfirmBaseUrl()
    {
        HttpUtil.requestFirst("index", BaseUrlBean.class, getActivity(), new MyResponseListener<BaseUrlBean>()
        {
            @Override
            public void onSuccess(BaseUrlBean result)
            {
                HttpUtil.changeBaseUrl(result.getUrl());
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }
}
