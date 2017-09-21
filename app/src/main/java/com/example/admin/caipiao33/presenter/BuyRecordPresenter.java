package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.bean.BuyRecordBean;
import com.example.admin.caipiao33.contract.IBuyRecordContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

import java.util.HashMap;

/**
 * Created by shaodongPC on 2017/8/9.
 */

public class BuyRecordPresenter implements IBuyRecordContract.Presenter
{
    private IBuyRecordContract.View mView;
    private boolean isFirst;

    public BuyRecordPresenter(IBuyRecordContract.View view)
    {
        this.mView = view;
    }

    @Override
    public void loadData(String type, String page)
    {
        mView.showLoadingLayout();
        requset(type, page, new MyResponseListener<BuyRecordBean>()
        {

            @Override
            public void onSuccess(BuyRecordBean result)
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
    public void refreshData(String type, String page)
    {
        requset(type, page, new MyResponseListener<BuyRecordBean>()
        {

            @Override
            public void onSuccess(BuyRecordBean result)
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
    public void loadMore(String type, String page)
    {
        requset(type, page, new MyResponseListener<BuyRecordBean>()
        {

            @Override
            public void onSuccess(BuyRecordBean result)
            {
                mView.updateMoreData(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {

            }
        });
    }

    private void requset(String type, String page, MyResponseListener listener)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("page", page);
        HttpUtil.requestSecond("user", "betList", map, BuyRecordBean.class, mView.getBaseActivity(), listener, null);
    }
}
