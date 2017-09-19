package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.bean.KaiJiangDTBean;
import com.example.admin.caipiao33.contract.IKaiJiangContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by cxy on 2017/8/2
 */

public class KaiJiangDTPresenter implements IKaiJiangContract.Presenter
{
    private final IKaiJiangContract.View mView;
    private View hideView;

    public KaiJiangDTPresenter(IKaiJiangContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void loadData()
    {
        mView.showLoadingLayout4Ami(hideView);
        HttpUtil.requestFirst("draw1", null, new TypeToken<ArrayList<KaiJiangDTBean>>()
        {
        }.getType(), mView.getBaseActivity(), new MyResponseListener<ArrayList<KaiJiangDTBean>>()
        {
            @Override
            public void onSuccess(ArrayList<KaiJiangDTBean> result)
            {
                mView.hideLoadingLayout4Ami(hideView);
                mView.update(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showLoadingLayoutError4Ami(hideView);
                mView.showErrorMsg(msg);
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
        HttpUtil.requestFirst("index", HomePageBean.class, mView.getBaseActivity(), new MyResponseListener<ArrayList<KaiJiangDTBean>>()
        {
            @Override
            public void onSuccess(ArrayList<KaiJiangDTBean> result)
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
        }, null);
    }
}
