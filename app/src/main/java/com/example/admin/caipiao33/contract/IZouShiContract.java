package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.GouCaiBean;

/**
 * Created by cxy on 2017/8/8
 */

public interface IZouShiContract
{
    interface View extends IBaseView
    {
        void update(GouCaiBean bean);
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();
    }
}
