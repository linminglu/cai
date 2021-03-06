package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.UserInfoBean;

/**
 * Created by cxy on 2017/8/2
 */

public interface IUserContract
{
    interface View extends IBaseView
    {
        void updateUsers(UserInfoBean bean);
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();
    }
}
