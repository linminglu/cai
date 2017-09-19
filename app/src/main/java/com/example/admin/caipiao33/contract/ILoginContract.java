package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/8/8
 */

public interface ILoginContract
{
    interface View extends IBaseView
    {
        void successFul(String result);

        void failed(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void getLogin(String userName, String password);
    }
}
