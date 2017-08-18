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
        void updateAmount(String amount);
        void submitSuccess();
    }

    interface Presenter extends IBasePresenter {
        void loadData(String num, String roomId, String playId, String playId1);
        void refreshLotteryData(String num);
        void refreshAmount();
        // gid彩种编码 roomId房间ID issue下注期数 betList下注列表
        void submit(String gid, String roomId, String issue, String betList);
    }
}
