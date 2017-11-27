package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.HomePageBean;

/**
 * Created by mac on 2017/8/1.
 */

public interface IHomePageContract
{
    interface View extends IBaseView
    {
        void updateHomePage(HomePageBean bean);

        void updateHomePage1(HomePageBean bean);

        void hideRefreshing();

        void updateServiceUrl(String url);

        void toBuyRoom(BuyRoomBean bean, String title);
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();

        void toAskService();

        void requestRoomData(String num, String title);

        void noTip(String id);
    }
}
