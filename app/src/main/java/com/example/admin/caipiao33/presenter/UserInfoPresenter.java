package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.bean.UserInfoBean;
import com.example.admin.caipiao33.contract.IUserContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/2
 */

public class UserInfoPresenter implements IUserContract.Presenter
{
    private final IUserContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public UserInfoPresenter(IUserContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void loadData()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");

        HttpUtil.requestSecond("user", "amount", map, UserInfoBean.class, mView.getBaseActivity(), new MyResponseListener<UserInfoBean>()
        {
            @Override
            public void onSuccess(UserInfoBean result)
            {
                if (isFirst)
                {
                    mView.hideLoadingLayout4Ami(hideView);
                    isFirst = false;
                }
                mView.updateUsers(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                if (isFirst)
                {
                    mView.showLoadingLayoutError4Ami(hideView);
                }
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }

    @Override
    public void refreshData()
    {

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "0");

        HttpUtil.requestSecond("user", "amount", map, UserInfoBean.class, mView.getBaseActivity(), new MyResponseListener<UserInfoBean>()
        {
            @Override
            public void onSuccess(UserInfoBean result)
            {
                mView.updateUsers(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }
}
