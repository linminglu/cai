package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.TiXianJiLuBean;
import com.example.admin.caipiao33.contract.ITiXianJiLuContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/22
 */

public class TiKuanJiLuPresenter implements ITiXianJiLuContract.Presenter
{
    private final ITiXianJiLuContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public TiKuanJiLuPresenter(ITiXianJiLuContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getTiXianJiLu(String type)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        if (!StringUtils.isEmpty2(type))
        {
            map.put("type", type);
        }

        HttpUtil.requestSecond("user", "withdrawList", map, TiXianJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<TiXianJiLuBean>()
        {
            @Override
            public void onSuccess(TiXianJiLuBean result)
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

        HttpUtil.requestSecond("user", "withdrawList", map, TiXianJiLuBean.class, mView.getBaseActivity(), new MyResponseListener<TiXianJiLuBean>()
        {
            @Override
            public void onSuccess(TiXianJiLuBean result)
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
