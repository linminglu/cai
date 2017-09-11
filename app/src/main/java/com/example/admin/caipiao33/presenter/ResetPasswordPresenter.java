package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.IFindPasswordContract;
import com.example.admin.caipiao33.contract.IResetPasswordContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/10
 */

public class ResetPasswordPresenter implements IResetPasswordContract.Presenter
{
    private final IResetPasswordContract.View mView;
    private View hideView;

    public ResetPasswordPresenter(IResetPasswordContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void resetPassword(String password, String password1)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("password1", password1);


        HttpUtil.requestSecond("password", "reset", null, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.successFul(result);
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
