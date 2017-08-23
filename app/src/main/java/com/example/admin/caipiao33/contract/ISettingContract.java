package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/8/23
 */

public interface ISettingContract
{
    interface View extends IBaseView
    {
        void updata(String result);

        void logoutOk(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void getSettingOp();

        void getLogout();
    }
}
