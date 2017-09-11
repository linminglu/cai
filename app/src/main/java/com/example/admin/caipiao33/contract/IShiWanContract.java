package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/9/11
 */

public interface IShiWanContract
{
    interface View extends IBaseView
    {
        void updata(String result);

        void loginOk(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void getShiWan();

        void shiWanLogin(String username, String password);
    }
}
