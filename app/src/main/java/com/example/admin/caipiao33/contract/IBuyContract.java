package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.bean.HomePageBean;

/**
 * Created by mac on 2017/8/1.
 */

public interface IBuyContract
{
    interface View extends IBaseView {
        void updateHomePage(BuyRoomBean bean);
        void updateLotteryData(GouCaiBean.DataBean result);
        void updateLotteryFailed();
    }

    interface Presenter extends IBasePresenter {
        void loadData(String num, String roomId, String playId, String playId1);
        void refreshLotteryData(String num);
    }
}
