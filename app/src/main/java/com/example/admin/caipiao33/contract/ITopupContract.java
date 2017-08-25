package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/8/24
 */

public interface ITopupContract
{
    interface View extends IBaseView
    {
        void updata(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void getTopup(String userName, String password);
    }
}
