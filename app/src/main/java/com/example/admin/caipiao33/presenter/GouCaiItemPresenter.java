package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IGouCaiContract;
import com.example.admin.caipiao33.contract.IGouCaiItemContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

import java.util.HashMap;

/**
 * Created by shaodong on 2017/8/3 0003.
 */

/**
 * 购彩页面Presenter
 */
public class GouCaiItemPresenter implements IGouCaiItemContract.Presenter
{
    private final IGouCaiItemContract.View mView;

    public GouCaiItemPresenter(IGouCaiItemContract.View view) {
        this.mView = view;
    }

    @Override
    public void refreshData(final String num, final int what)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", num);
        HttpUtil.requestSecond("hall", "single", map, GouCaiBean.DataBean.class, mView.getBaseActivity(), new MyResponseListener<GouCaiBean.DataBean>()
        {
            @Override
            public void onSuccess(GouCaiBean.DataBean result)
            {
                mView.updateItem(result, what);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.refreshDataFailed(num, what);
            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }

    @Override
    public void requestRoomData(String num, final String title)
    {
        mView.showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", num);
        HttpUtil.requestFirst("buy", map, BuyRoomBean.class, mView.getBaseActivity(), new MyResponseListener<BuyRoomBean>()
        {
            @Override
            public void onSuccess(BuyRoomBean result)
            {
                mView.toBuyRoom(result, title);
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }
}
