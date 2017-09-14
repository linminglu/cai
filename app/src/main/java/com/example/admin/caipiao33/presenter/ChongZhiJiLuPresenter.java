package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.ChongZhiJiLuBean;
import com.example.admin.caipiao33.bean.ZhangHuMingXiBean;
import com.example.admin.caipiao33.contract.IChongZhiJiLuContract;
import com.example.admin.caipiao33.contract.IZhangHuMingXiContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/22
 */

public class ChongZhiJiLuPresenter implements IChongZhiJiLuContract.Presenter
{
    private final IChongZhiJiLuContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public ChongZhiJiLuPresenter(IChongZhiJiLuContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getChongZhiJiLu(String type)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        if (!StringUtils.isEmpty2(type))
        {
            map.put("type", type);
        }

        HttpUtil.requestSecond("user", "rechargeList", map, ChongZhiJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<ChongZhiJiLuBean>()
        {
            @Override
            public void onSuccess(ChongZhiJiLuBean result)
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
    public void getMoreJiLu(String type, int page)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page + "");
        if (!StringUtils.isEmpty2(type))
        {
            map.put("type", type);
        }

        HttpUtil.requestSecond("user", "rechargeList", map, ChongZhiJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<ChongZhiJiLuBean>()
        {
            @Override
            public void onSuccess(ChongZhiJiLuBean result)
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
