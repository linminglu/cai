package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IZouShiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

/**
 * Created by cxy on 2017/8/17
 */

public class ZouShiPresenter implements IZouShiContract.Presenter
{
    private final IZouShiContract.View mView;
    private View hideView;

    public ZouShiPresenter(IZouShiContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void loadData()
    {
        mView.showLoadingLayout4Ami(hideView);
        baseRequest(new MyResponseListener<GouCaiBean>()
        {
            @Override
            public void onSuccess(GouCaiBean result)
            {
                mView.hideLoadingLayout4Ami(hideView);
                mView.update(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showLoadingLayoutError4Ami(hideView);
            }

            @Override
            public void onFinish()
            {

            }
        });
    }

    @Override
    public void refreshData()
    {
        baseRequest(new MyResponseListener<GouCaiBean>()
        {
            @Override
            public void onSuccess(GouCaiBean result)
            {
                mView.update(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {

            }
        });
    }

    private void baseRequest(MyResponseListener listener)
    {
        HttpUtil.requestFirst("hall", GouCaiBean.class, mView.getBaseActivity(), listener, null);
    }
}
