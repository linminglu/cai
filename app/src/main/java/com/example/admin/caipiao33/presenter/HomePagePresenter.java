package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.contract.IHomePageContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

/**
 * Created by mac on 2017/8/1.
 */

public class HomePagePresenter implements IHomePageContract.Presenter
{
    private final IHomePageContract.View mView;

    public HomePagePresenter(IHomePageContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadData()
    {
        mView.showLoadingLayout();
        baseRequest(new MyResponseListener<HomePageBean>()
        {
            @Override
            public void onSuccess(HomePageBean result)
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
        baseRequest(new MyResponseListener<HomePageBean>()
        {
            @Override
            public void onSuccess(HomePageBean result)
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

    @Override
    public void toAskService()
    {
        mView.showLoadingDialog();
        HttpUtil.requestFirst("kefu", String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.updateServiceUrl(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }

    private void baseRequest(MyResponseListener listener){
        HttpUtil.requestFirst("index", HomePageBean.class, mView.getBaseActivity(), listener, null);
    }
}
