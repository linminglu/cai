package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.ChongZhiDetailBean;
import com.example.admin.caipiao33.contract.IChongZhiDetailContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/8/8
 */

public class ChongZhiDetailPresenter implements IChongZhiDetailContract.Presenter
{
    private final IChongZhiDetailContract.View mView;
    private View hideView;

    public ChongZhiDetailPresenter(IChongZhiDetailContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getChongZhiDetail(String id)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.requestSecond("user", "rechargeDetail", map, ChongZhiDetailBean.class, mView.getBaseActivity(), new MyResponseListener<ChongZhiDetailBean>()
        {
            @Override
            public void onSuccess(ChongZhiDetailBean result)
            {
                mView.updata(result);
                mView.hideLoadingLayout();
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
            }
        }, null);
    }
}
