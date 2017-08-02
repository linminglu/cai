package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.HomePageBean;

/**
 * Created by cxy on 2017/8/2
 */

public interface IUserContract
{
    interface View extends IBaseView
    {
        void updateUsers(HomePageBean bean);
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();
    }
}
