package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.TiKuanBean;
import com.example.admin.caipiao33.contract.ILoginContract;
import com.example.admin.caipiao33.contract.ITiKuanContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

/**
 * Created by cxy on 2017/9/5
 */

public class TiKuanPresenter implements ITiKuanContract.Presenter
{
    private final ITiKuanContract.View mView;
    private View hideView;

    public TiKuanPresenter(ITiKuanContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getTiKuan()
    {
        mView.showLoadingDialog(false);

        HttpUtil.requestSecond("user", "withdraw", null, TiKuanBean.class, mView.getBaseActivity(), new MyResponseListener<TiKuanBean>()
        {
            @Override
            public void onSuccess(TiKuanBean result)
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

    @Override
    public void submitTiKuan(String amount, String password)
    {
        mView.showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("money", amount);
        map.put("password", password);

        HttpUtil.requestSecond("user", "withdrawSubmit", map, String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.tiKuanOk(result);
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
