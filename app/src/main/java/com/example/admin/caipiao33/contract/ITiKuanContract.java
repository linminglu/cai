package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.TiKuanBean;

/**
 * Created by cxy on 2017/9/5
 */

public interface ITiKuanContract
{
    interface View extends IBaseView
    {
        void updata(TiKuanBean result);

        void tiKuanOk(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void getTiKuan();

        void submitTiKuan(String amount, String password);
    }
}
