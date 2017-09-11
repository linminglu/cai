package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.ChongZhiDetailBean;
import com.example.admin.caipiao33.bean.TiKuanDetailBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface ITiKuanDetailContract
{
    interface View extends IBaseView
    {
        void updata(TiKuanDetailBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getTiKuanDetail(String id);
    }
}
