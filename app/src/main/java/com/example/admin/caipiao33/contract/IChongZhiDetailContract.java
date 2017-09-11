package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.ChongZhiDetailBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface IChongZhiDetailContract
{
    interface View extends IBaseView
    {
        void updata(ChongZhiDetailBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getChongZhiDetail(String id);
    }
}
