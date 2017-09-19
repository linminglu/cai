package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.QianDaoJiLuBean;

/**
 * Created by cxy on 2017/8/22
 */

public interface IQianDaoJiLuContract
{
    interface View extends IBaseView
    {
        void updata(QianDaoJiLuBean result);

        void loadmore(QianDaoJiLuBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getQianDaoJiLu();

        void getMoreJiLu(int page);
    }
}
