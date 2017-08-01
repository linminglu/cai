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
        HttpUtil.requestFirst("index", HomePageBean.class, mView.getBaseActivity(), new MyResponseListener<HomePageBean>()
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
        }, null);
    }

    @Override
    public void refreshData()
    {

    }
}
