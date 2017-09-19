package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.RegisterBean;
import com.example.admin.caipiao33.bean.RegisterSubmitBean;
import com.example.admin.caipiao33.contract.IRegisterContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/23
 */

public class RegisterPresenter implements IRegisterContract.Presenter
{
    private final IRegisterContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public RegisterPresenter(IRegisterContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getRegisterOp()
    {
        if (!isFirst)
        {
            mView.showLoadingDialog(false);
        }
        HttpUtil.requestSecond("reg", "pre", null, RegisterBean.class, mView.getBaseActivity(), new MyResponseListener<RegisterBean>()
        {
            @Override
            public void onSuccess(RegisterBean result)
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
                if (isFirst)
                {
                    mView.hideLoadingLayout();
                    isFirst = false;
                }
                else
                {
                    mView.hideLoadingDialog();
                }
            }
        }, null);
    }

    @Override
    public void getVerifycode()
    {
        mView.showLoadingDialog(false);
        HttpUtil.requestSecond("reg", "pre", null, RegisterBean.class, mView.getBaseActivity(), new MyResponseListener<RegisterBean>()
        {
            @Override
            public void onSuccess(RegisterBean result)
            {
                mView.updataVerifycode(result);
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
    public void submitRegister(String username, String password, String verifycode, String tuijian, String qq, String phonenum, String email)
    {
        mView.showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("userName", username);
        map.put("password", password);
        map.put("vcode", verifycode);
        map.put("phone", !StringUtils.isEmpty2(phonenum) ? phonenum : "");
        map.put("email", !StringUtils.isEmpty2(email) ? email : "");
        map.put("qq", !StringUtils.isEmpty2(qq) ? qq : "");
        map.put("tjr", !StringUtils.isEmpty2(tuijian) ? tuijian : "");


        HttpUtil.requestSecond("reg", "submit", map, RegisterSubmitBean.class, mView.getBaseActivity(), new MyResponseListener<RegisterSubmitBean>()
        {
            @Override
            public void onSuccess(RegisterSubmitBean result)
            {
                mView.registerOk(result);
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
