package com.example.admin.caipiao33;

import android.support.v4.app.Fragment;

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
}
