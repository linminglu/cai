package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/8
 */

public class LoginPresenter implements ILoginContract.Presenter
{
    private final ILoginContract.View mView;
    private View hideView;

    public LoginPresenter(ILoginContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getLogin(String userName, String password)
    {
        mView.showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);


        HttpUtil.requestLogin(map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.successFul(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.failed(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }
}
