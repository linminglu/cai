package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.contract.IUserContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

/**
 * Created by cxy on 2017/8/2
 */

public class UserInfoPresenter implements IUserContract.Presenter
{
    private final IUserContract.View mView;
    private View hideView;

    public UserInfoPresenter(IUserContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void loadData()
    {
        mView.showLoadingLayout4Ami(hideView);
        HttpUtil.requestFirst("index", HomePageBean.class, mView.getBaseActivity(), new MyResponseListener<HomePageBean>()
        {
            @Override
            public void onSuccess(HomePageBean result)
            {
                mView.hideLoadingLayout4Ami(hideView);
                mView.updateUsers(result);
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
        }, null);
    }

    @Override
    public void refreshData()
    {
        HttpUtil.requestFirst("index", HomePageBean.class, mView.getBaseActivity(), new MyResponseListener<HomePageBean>()
        {
            @Override
            public void onSuccess(HomePageBean result)
            {
                mView.updateUsers(result);
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
        }, null);
    }
}
