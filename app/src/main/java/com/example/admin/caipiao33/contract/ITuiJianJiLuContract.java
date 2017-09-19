package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.TuiJianJiLuBean;

/**
 * Created by cxy on 2017/8/22
 */

public interface ITuiJianJiLuContract
{
    interface View extends IBaseView
    {
        void updata(TuiJianJiLuBean result);

        void loadmore(TuiJianJiLuBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getTuiJianJiLu();

        void getMoreJiLu(int page);
    }
}
