package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.ChongZhiJiLuBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface IChongZhiJiLuContract
{
    interface View extends IBaseView
    {
        void updata(ChongZhiJiLuBean result);

        void loadmore(ChongZhiJiLuBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getChongZhiJiLu(String type);

        void getMoreJiLu(String type, int page);
    }
}
