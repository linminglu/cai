package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.QianDaoJiLuBean;
import com.example.admin.caipiao33.contract.IQianDaoJiLuContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/22
 */

public class QianDaoJiLuPresenter implements IQianDaoJiLuContract.Presenter
{
    private final IQianDaoJiLuContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public QianDaoJiLuPresenter(IQianDaoJiLuContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getQianDaoJiLu()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");

        HttpUtil.requestSecond("user", "checkinList", map, QianDaoJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<QianDaoJiLuBean>()
        {
            @Override
            public void onSuccess(QianDaoJiLuBean result)
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

        HttpUtil.requestSecond("user", "checkinList", map, QianDaoJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<QianDaoJiLuBean>()
        {
            @Override
            public void onSuccess(QianDaoJiLuBean result)
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
