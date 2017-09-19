package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.TuiJianJiLuBean;
import com.example.admin.caipiao33.contract.ITuiJianJiLuContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/22
 */

public class TuiJianJiLuPresenter implements ITuiJianJiLuContract.Presenter
{
    private final ITuiJianJiLuContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public TuiJianJiLuPresenter(ITuiJianJiLuContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getTuiJianJiLu()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");

        HttpUtil.requestSecond("user", "spreadList", map, TuiJianJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<TuiJianJiLuBean>()
        {
            @Override
            public void onSuccess(TuiJianJiLuBean result)
            {
                if (isFirst)
                {
                    mView.hideLoadingLayout4Ami(hideView);
                    isFirst = false;
                }
                mView.updata(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                if (isFirst)
                {
                    mView.showLoadingLayoutError4Ami(hideView);
                }
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }

    @Override
    public void getMoreJiLu(int page)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");

        HttpUtil.requestSecond("user", "checkinList", map, TuiJianJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<TuiJianJiLuBean>()
        {
            @Override
            public void onSuccess(TuiJianJiLuBean result)
            {
                mView.loadmore(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }
}
