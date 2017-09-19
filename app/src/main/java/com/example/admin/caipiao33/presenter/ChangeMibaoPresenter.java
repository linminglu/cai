package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.contract.IChangeMibaoContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/10
 */

public class ChangeMibaoPresenter implements IChangeMibaoContract.Presenter
{
    private final IChangeMibaoContract.View mView;
    private View hideView;

    public ChangeMibaoPresenter(IChangeMibaoContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void changeMibao(String question, String answer, String nAnswer)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("question", question);
        map.put("answer", answer);
        map.put("nAnswer", nAnswer);
        HttpUtil.requestSecond("setting", "qaSubmit", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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

    @Override
    public void setMibao(String question, String answer)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("question", question);
        map.put("answer", answer);
        HttpUtil.requestSecond("setting", "qaSubmit", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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

    @Override
    public void getMibao()
    {
        mView.showLoadingDialog(false);
        HttpUtil.requestSecond("setting", "getQa", null, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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
}
