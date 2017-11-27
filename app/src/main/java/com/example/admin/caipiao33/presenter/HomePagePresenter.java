package com.example.admin.caipiao33.presenter;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.contract.IHomePageContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mac on 2017/8/1.
 */

public class HomePagePresenter implements IHomePageContract.Presenter
{
    private final IHomePageContract.View mView;
    private boolean isFirst = true;

    public HomePagePresenter(IHomePageContract.View view)
    {
        this.mView = view;
    }

    @Override
    public void loadData()
    {
        if (isFirst)
        {
            mView.showLoadingLayout();
        }
        baseRequest(new MyResponseListener<HomePageBean>()
        {
            @Override
            public void onSuccess(HomePageBean result)
            {
                if (isFirst)
                {
                    isFirst = false;
                    mView.hideLoadingLayout();
                }
                List<HomePageBean.TypeListBean> typeList = result.getTypeList();
                if (null == typeList || typeList.size() == 0)
                {
                    mView.showLoadingLayoutError();
                    return;
                }
                mView.updateHomePage(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showLoadingLayoutError();
            }

            @Override
            public void onFinish()
            {

            }
        });
    }

    @Override
    public void refreshData()
    {
        baseRequest(new MyResponseListener<HomePageBean>()
        {
            @Override
            public void onSuccess(HomePageBean result)
            {
                mView.updateHomePage1(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideRefreshing();
            }
        });
    }

    @Override
    public void toAskService()
    {
        mView.showLoadingDialog();
        HttpUtil.requestFirst("kefu", String.class, mView.getBaseActivity(), new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                mView.updateServiceUrl(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }

    private void baseRequest(MyResponseListener listener)
    {
        HttpUtil.requestFirst("index", HomePageBean.class, mView.getBaseActivity(), listener, null);
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
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }

    @Override
    public void noTip(String id)
    {
        mView.showLoadingDialog();
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.requestSecond("notice", "noTip", map, null, mView.getBaseActivity(), new MyResponseListener()
        {
            @Override
            public void onSuccess(Object result)
            {
            }

            @Override
            public void onFailed(int code, String msg)
            {
                mView.showErrorMsg(msg);
            }

            @Override
            public void onFinish()
            {
                mView.hideLoadingDialog();
            }
        }, null);
    }
}
