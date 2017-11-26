package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.AliPayBean;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.bean.TypeBean;
import com.example.admin.caipiao33.contract.IZouShiContract;
import com.example.admin.caipiao33.fragment.adapter.AliPayAdapter;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

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
        HttpUtil.requestSecond("trend", "typeList", null, new TypeToken<ArrayList<TypeBean>>()
        {
        }.getType(), mView.getBaseActivity(), new MyResponseListener<ArrayList<TypeBean>>()
        {
            @Override
            public void onSuccess(ArrayList<TypeBean> result)
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
        }, null);
    }

    @Override
    public void refreshData()
    {
        HttpUtil.requestSecond("trend", "typeList", null, new TypeToken<ArrayList<TypeBean>>()
        {
        }.getType(), mView.getBaseActivity(), new MyResponseListener<ArrayList<TypeBean>>()
        {
            @Override
            public void onSuccess(ArrayList<TypeBean> result)
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

    @Override
    public void requestRoomData(String num, final String title)
    {
        mView.showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", num);
        HttpUtil.requestFirst("buy", map, BuyRoomBean.class, mView.getBaseActivity(), new MyResponseListener<BuyRoomBean>()
        {
            @Override
            public void onSuccess(BuyRoomBean result)
            {
                mView.toBuyRoom(result, title);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }
}
