package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.TopupBean;
import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.contract.ITopupContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/8
 */

public class TopupPresenter implements ITopupContract.Presenter
{
    private final ITopupContract.View mView;
    private View hideView;

    public TopupPresenter(ITopupContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getTopup()
    {
        mView.showLoadingDialog(false);
        HttpUtil.requestSecond("user", "recharge", null, TopupBean.class, mView.getBaseActivity(), new MyResponseListener<TopupBean>()
        {
            @Override
            public void onSuccess(TopupBean result)
            {
                mView.updata(result);
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
