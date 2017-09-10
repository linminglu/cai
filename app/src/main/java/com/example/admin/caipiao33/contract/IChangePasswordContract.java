package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/9/10
 */

public interface IChangePasswordContract
{
    interface View extends IBaseView
    {
        void successFul(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void changePassword(String oPasswd, String passwd, String nPasswd);
    }
}
