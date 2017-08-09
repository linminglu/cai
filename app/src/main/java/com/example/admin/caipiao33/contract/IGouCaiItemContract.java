package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;

/**
 * Created by mac on 2017/8/1.
 */

public interface IGouCaiItemContract
{
    interface View extends IBaseView {
        void updateItem(GouCaiBean.DataBean bean, int what);
        void refreshDataFailed(String num, int what);
        void toBuyRoom(BuyRoomBean bean, String title);
    }

    interface Presenter extends IBasePresenter {
        void refreshData(String num, int what);
        void requestRoomData(String num, String title);
    }
}
