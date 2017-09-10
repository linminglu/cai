package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.ChangeBankCardBean;

/**
 * Created by cxy on 2017/9/10
 */

public interface IChangeBankCardContract
{
    interface View extends IBaseView
    {
        void successFul(String result);

        void updata(ChangeBankCardBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void changeBankCard(String password, String accountName, String bankNum, String bankName, String provice, String city, String remark);

        void getBankCard();
    }
}
