package com.example.admin.caipiao33;

/**
 * Created by mac on 2016/3/24.
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
}
