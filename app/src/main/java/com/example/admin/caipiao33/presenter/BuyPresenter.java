package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IBuyContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by shaodongPC on 2017/8/9.
 */

public class BuyPresenter implements IBuyContract.Presenter
{
    private final IBuyContract.View mView;
    private boolean isFirst = true;

    public BuyPresenter(IBuyContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadData(String num, String roomId, String playId, String playId1)
    {
        if (!isFirst) {
            mView.showLoadingDialog();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", num);
        map.put("roomId", roomId);
        if (!StringUtils.isEmpty(playId)) {
            map.put("playId", playId);
        }
        if (!StringUtils.isEmpty(playId1)) {
            map.put("playId1", playId1);
        }
        HttpUtil.requestFirst("buy", map, BuyRoomBean.class, mView.getBaseActivity(), new MyResponseListener<BuyRoomBean>()
        {
            @Override
            public void onSuccess(BuyRoomBean result)
            {
                if (isFirst) {
                    mView.hideLoadingLayout();
                    isFirst = false;
                }
                mView.updateHomePage(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                if (isFirst) {
                    mView.showLoadingLayoutError();
                }
            }

            @Override
            public void onFinish()
            {
                if (!isFirst) {
                    mView.hideLoadingDialog();
                }
            }
        }, null);
    }

    @Override
    public void refreshLotteryData(String num)
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", num);
        HttpUtil.requestSecond("hall", "single", map, GouCaiBean.DataBean.class, mView.getBaseActivity(), new MyResponseListener<GouCaiBean.DataBean>()
        {
            @Override
            public void onSuccess(GouCaiBean.DataBean result)
            {
                mView.updateLotteryData(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.updateLotteryFailed();
            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }
}
