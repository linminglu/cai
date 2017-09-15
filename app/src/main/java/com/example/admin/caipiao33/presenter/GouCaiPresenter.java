package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IGouCaiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

/**
 * Created by shaodong on 2017/8/3 0003.
 */

/**
 * 购彩页面Presenter
 */
public class GouCaiPresenter implements IGouCaiContract.Presenter
{
    private final IGouCaiContract.View mView;

    public GouCaiPresenter(IGouCaiContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadData()
    {
        mView.showLoadingLayout();
        baseRequest(new MyResponseListener<GouCaiBean>()
        {
            @Override
            public void onSuccess(GouCaiBean result)
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
        baseRequest(new MyResponseListener<GouCaiBean>()
        {
            @Override
            public void onSuccess(GouCaiBean result)
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

    private void baseRequest(MyResponseListener listener) {
        HttpUtil.requestFirst("hall", GouCaiBean.class, mView.getBaseActivity(), listener, null);
    }

}
