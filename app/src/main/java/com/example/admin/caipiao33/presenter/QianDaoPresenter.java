package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.QianDaoBean;
import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.contract.IQianDaoContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/8
 */

public class QianDaoPresenter implements IQianDaoContract.Presenter
{
    private final IQianDaoContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public QianDaoPresenter(IQianDaoContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getQianDao()
    {
        HttpUtil.requestSecond("user", "checkin", null, QianDaoBean.class, mView.getBaseActivity(), new MyResponseListener<QianDaoBean>()
        {
            @Override
            public void onSuccess(QianDaoBean result)
            {
                if (isFirst)
                {
                    mView.hideLoadingLayout();
                    isFirst = false;
                }
                mView.updata(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                if (isFirst)
                {
                    mView.showLoadingLayoutError();
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
    public void submitQianDao()
    {
        mView.showLoadingDialog(false);
        HttpUtil.requestSecond("user", "checkinSubmit", null, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.submit(result);
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
