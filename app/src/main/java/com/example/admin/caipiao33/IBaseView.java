package com.example.admin.caipiao33;

import android.view.View;

/**
 * Created by mac on 2017/2/9.
 */

public interface IBaseView
{
    BaseActivity getBaseActivity();

    /**
     * 显示加载中的Dialog
     *
     * @deprecated Use {@link #showLoadingDialog(boolean)}
     */
    @Deprecated
    void showLoadingDialog();

    /**
     * 显示加载中的Dialog，参数isCanceledOnTouchOutside能不能点击外部区域，默认为false
     *
     * @param isCanceledOnTouchOutside
     */
    void showLoadingDialog(boolean isCanceledOnTouchOutside);

    void hideLoadingDialog();

    void showLoadingLayout();

    void hideLoadingLayout();

    void showLoadingLayoutError();

    void showLoadingLayout4Ami(View view);

    void hideLoadingLayout4Ami(View view);

    void showLoadingLayoutError4Ami(View view);

    void showErrorMsg(String msg);
}
