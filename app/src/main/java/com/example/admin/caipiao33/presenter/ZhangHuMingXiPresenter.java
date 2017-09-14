package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.TuiJianJiLuBean;
import com.example.admin.caipiao33.bean.ZhangHuMingXiBean;
import com.example.admin.caipiao33.contract.ITuiJianJiLuContract;
import com.example.admin.caipiao33.contract.IZhangHuMingXiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/22
 */

public class ZhangHuMingXiPresenter implements IZhangHuMingXiContract.Presenter
{
    private final IZhangHuMingXiContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public ZhangHuMingXiPresenter(IZhangHuMingXiContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getZhangHuMingXi(String type)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        map.put("type", type);

        HttpUtil.requestSecond("user", "accountList", map, ZhangHuMingXiBean.class, mView.getBaseActivity(), new MyResponseListener<ZhangHuMingXiBean>()
        {
            @Override
            public void onSuccess(ZhangHuMingXiBean result)
            {
                if (isFirst)
                {
                    mView.hideLoadingLayout4Ami(hideView);
                    isFirst = false;
                }
                mView.updata(result);
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
                mView.hideLoadingDialog();
            }
        }, null);
    }

    @Override
    public void getMoreMingXi(String type, int page)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        map.put("type", type);

        HttpUtil.requestSecond("user", "accountList", map, ZhangHuMingXiBean.class, mView.getBaseActivity(), new MyResponseListener<ZhangHuMingXiBean>()
        {
            @Override
            public void onSuccess(ZhangHuMingXiBean result)
            {
                mView.loadmore(result);
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
