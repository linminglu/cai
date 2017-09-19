package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by mac on 2017/8/1.
 */

public interface IPromotionsContract
{
    interface View extends IBaseView
    {
        void updateHomePage(Object bean);

        void hideRefreshing();
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();
    }
}
