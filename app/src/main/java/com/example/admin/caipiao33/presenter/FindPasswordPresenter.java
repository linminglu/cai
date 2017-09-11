package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.IFindPasswordContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/10
 */

public class FindPasswordPresenter implements IFindPasswordContract.Presenter
{
    private final IFindPasswordContract.View mView;
    private View hideView;

    public FindPasswordPresenter(IFindPasswordContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getPassword(String userName, String question, String answer)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("question", question);
        map.put("answer", answer);


        HttpUtil.requestSecond("password", "verify", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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
