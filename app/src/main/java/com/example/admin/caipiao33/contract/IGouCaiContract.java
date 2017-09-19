package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.GouCaiBean;

/**
 * Created by mac on 2017/8/1.
 */

public interface IGouCaiContract
{
    interface View extends IBaseView
    {
        void updateHomePage(GouCaiBean bean);

        void hideRefreshing();
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();
    }
}
