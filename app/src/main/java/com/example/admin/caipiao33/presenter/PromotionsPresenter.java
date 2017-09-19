package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.contract.IPromotionsContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

/**
 * Created by mac on 2017/8/1.
 */

public class PromotionsPresenter implements IPromotionsContract.Presenter
{
    private final IPromotionsContract.View mView;

    public PromotionsPresenter(IPromotionsContract.View view)
    {
        this.mView = view;
    }

    @Override
    public void loadData()
    {
        mView.showLoadingLayout();
        baseRequest(new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.hideLoadingLayout();
                mView.updateHomePage(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showLoadingLayoutError();
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
        baseRequest(new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.updateHomePage(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {
                mView.hideRefreshing();
            }
        });
    }

    private void baseRequest(MyResponseListener listener)
    {
        HttpUtil.requestFirst("activity", String.class, mView.getBaseActivity(), listener, null);
    }

}
