package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.ChangeBankCardBean;
import com.example.admin.caipiao33.contract.IChangeBankCardContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/10
 */

public class ChangeBankCardPresenter implements IChangeBankCardContract.Presenter
{
    private final IChangeBankCardContract.View mView;
    private View hideView;

    public ChangeBankCardPresenter(IChangeBankCardContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void changeBankCard(String password, String accountName, String bankNum, String bankName, String provice, String city, String remark)
    {
        mView.showLoadingDialog(false);
        HashMap<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("accountName", accountName);
        map.put("bankNum", bankNum);
        map.put("bankName", bankName);
        map.put("provice", provice);
        map.put("city", city);
        map.put("remark", remark);
        HttpUtil.requestSecond("setting", "bindBank", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
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
    public void getBankCard()
    {
        mView.showLoadingDialog(false);
        HttpUtil.requestSecond("setting", "getBank", null, ChangeBankCardBean.class, mView.getBaseActivity(), new MyResponseListener<ChangeBankCardBean>()
        {
            @Override
            public void onSuccess(ChangeBankCardBean result)
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
