package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/9/10
 */

public interface IResetPasswordContract
{
    interface View extends IBaseView
    {
        void successFul(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void resetPassword(String password, String password1);
    }
}
