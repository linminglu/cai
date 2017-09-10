package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.IChangePasswordContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/10
 */

public class ChangePasswordPresenter implements IChangePasswordContract.Presenter
{
    private final IChangePasswordContract.View mView;
    private View hideView;

    public ChangePasswordPresenter(IChangePasswordContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void changePassword(String oPasswd, String passwd, String nPasswd)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("oPasswd", oPasswd);
        map.put("passwd", passwd);
        map.put("nPasswd", nPasswd);
        HttpUtil.requestSecond("setting", "loginPasswd", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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
