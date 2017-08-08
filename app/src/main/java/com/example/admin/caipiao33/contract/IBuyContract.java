package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.HomePageBean;

/**
 * Created by mac on 2017/8/1.
 */

public interface IBuyContract
{
    interface View extends IBaseView {
        void updateHomePage(Object bean);
    }

    interface Presenter extends IBasePresenter {
        void loadData();
    }
}
