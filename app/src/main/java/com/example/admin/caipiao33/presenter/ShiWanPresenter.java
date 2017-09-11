package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.IShiWanContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/10
 */

public class ShiWanPresenter implements IShiWanContract.Presenter
{
    private final IShiWanContract.View mView;
    private View hideView;

    public ShiWanPresenter(IShiWanContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getShiWan()
    {
        mView.showLoadingDialog(false);

        HttpUtil.requestSecond("free", "userCode", null, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
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

    @Override
    public void shiWanLogin(String userName, String passWord)
    {
        mView.showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", passWord);


        HttpUtil.requestSecond("free", "submit", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.loginOk(result);
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
