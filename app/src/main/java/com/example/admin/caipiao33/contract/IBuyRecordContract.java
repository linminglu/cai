package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.BuyRecordBean;

/**
 * Created by mac on 2017/8/1.
 */

public interface IBuyRecordContract
{
    interface View extends IBaseView
    {
        void updateHomePage(BuyRecordBean bean);

        void updateMoreData(BuyRecordBean bean);

        void hideRefreshing();
    }

    interface Presenter extends IBasePresenter
    {
        void loadData(String type, String page);

        void refreshData(String type, String page);

        void loadMore(String type, String page);
    }
}
