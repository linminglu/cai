package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.QianDaoBean;

/**
 * Created by cxy on 2017/8/8
 */

public interface IQianDaoContract
{
    interface View extends IBaseView
    {
        void updata(QianDaoBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getQianDao();
    }
}
