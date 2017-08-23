package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.contract.ISettingContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by cxy on 2017/8/23
 */

public class SettingPresenter implements ISettingContract.Presenter
{
    private final ISettingContract.View mView;
    private View hideView;

    public SettingPresenter(ISettingContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getSettingOp()
    {

    }

    @Override
    public void getLogout()
    {
        mView.showLoadingDialog(false);
        HttpUtil.requestFirst("logout", String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.logoutOk(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
                mView.logoutOk(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }
}
