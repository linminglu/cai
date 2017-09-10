package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.IChangeTiKuanPasswordContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/5
 */

public class ChangeTiKuanPasswordPresenter implements IChangeTiKuanPasswordContract.Presenter
{
    private final IChangeTiKuanPasswordContract.View mView;
    private View hideView;

    public ChangeTiKuanPasswordPresenter(IChangeTiKuanPasswordContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void changeTiKuanPassword(String oPasswd, String passwd)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("oPasswd", oPasswd);
        map.put("passwd", passwd);
        HttpUtil.requestSecond("setting", "withdrawPasswd", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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
