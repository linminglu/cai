package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.TuiJianBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface ITuiJianContract
{
    interface View extends IBaseView
    {
        void updata(TuiJianBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getTuiJian();
    }
}
