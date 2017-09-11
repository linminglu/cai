package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.GeRenXiaoXiBean;
import com.example.admin.caipiao33.contract.IGeRenXiaoXiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/22
 */

public class GeRenXiaoXiPresenter implements IGeRenXiaoXiContract.Presenter
{
    private final IGeRenXiaoXiContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public GeRenXiaoXiPresenter(IGeRenXiaoXiContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getGeRenXiaoXi()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");

        HttpUtil.requestSecond("user", "msg", map, GeRenXiaoXiBean.class, mView.getBaseActivity(), new MyResponseListener<GeRenXiaoXiBean>()
        {
            @Override
            public void onSuccess(GeRenXiaoXiBean result)
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
                    isFirst = false;
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
    public void getMoreXiaoXi(int page)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");

        HttpUtil.requestSecond("user", "msg", map, GeRenXiaoXiBean.class, mView.getBaseActivity(), new MyResponseListener<GeRenXiaoXiBean>()
        {
            @Override
            public void onSuccess(GeRenXiaoXiBean result)
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
