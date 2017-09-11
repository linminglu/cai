package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.ZhangHuMingXiBean;

/**
 * Created by cxy on 2017/9/11
 */

public interface IZhangHuMingXiContract
{
    interface View extends IBaseView
    {
        void updata(ZhangHuMingXiBean result);

        void loadmore(ZhangHuMingXiBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getZhangHuMingXi(String type);

        void getMoreMingXi(String type, int page);
    }
}
