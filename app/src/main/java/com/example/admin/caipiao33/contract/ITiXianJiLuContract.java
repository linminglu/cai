package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.ChongZhiJiLuBean;
import com.example.admin.caipiao33.bean.TiXianJiLuBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface ITiXianJiLuContract
{
    interface View extends IBaseView
    {
        void updata(TiXianJiLuBean result);

        void loadmore(TiXianJiLuBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getTiXianJiLu(String type);

        void getMoreJiLu(String type, int page);
    }
}
