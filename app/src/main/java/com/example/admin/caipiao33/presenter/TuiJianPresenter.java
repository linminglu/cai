package com.example.admin.caipiao33.presenter;

import android.view.View;

import com.example.admin.caipiao33.bean.TuiJianBean;
import com.example.admin.caipiao33.contract.ITuiJianContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

/**
 * Created by cxy on 2017/9/11
 */

public class TuiJianPresenter implements ITuiJianContract.Presenter
{
    private final ITuiJianContract.View mView;
    private View hideView;
    private boolean isFirst = true;

    public TuiJianPresenter(ITuiJianContract.View view, View hideView)
    {
        this.mView = view;
        this.hideView = hideView;
    }

    @Override
    public void getTuiJian()
    {
        HttpUtil.requestSecond("user", "spread", null, TuiJianBean.class, mView.getBaseActivity(), new MyResponseListener<TuiJianBean>()
        {
            @Override
            public void onSuccess(TuiJianBean result)
            {
                if (isFirst)
                {
                    mView.hideLoadingLayout();
                    isFirst = false;
                }
                mView.updata(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                if (isFirst)
                {
                    mView.showLoadingLayoutError();
                    isFirst = false;
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
}
