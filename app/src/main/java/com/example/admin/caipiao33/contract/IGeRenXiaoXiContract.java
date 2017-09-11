package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.GeRenXiaoXiBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface IGeRenXiaoXiContract
{
    interface View extends IBaseView
    {
        void updata(GeRenXiaoXiBean result);

        void loadmore(GeRenXiaoXiBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getGeRenXiaoXi();

        void getMoreXiaoXi(int page);
    }
}
