package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.KaiJiangDTBean;
import com.example.admin.caipiao33.bean.UserInfoBean;

import java.util.ArrayList;

/**
 * Created by cxy on 2017/8/8
 */

public interface IKaiJiangContract
{
    interface View extends IBaseView
    {
        void update(ArrayList<KaiJiangDTBean> bean);
    }

    interface Presenter extends IBasePresenter
    {
        void loadData();

        void refreshData();
    }
}
