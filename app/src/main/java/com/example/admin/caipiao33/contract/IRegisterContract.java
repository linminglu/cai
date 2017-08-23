package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;
import com.example.admin.caipiao33.bean.RegisterBean;
import com.example.admin.caipiao33.bean.RegisterSubmitBean;

/**
 * Created by cxy on 2017/8/23
 */

public interface IRegisterContract
{
    interface View extends IBaseView
    {
        void updata(RegisterBean result);

        void updataVerifycode(RegisterBean result);

        void registerOk(RegisterSubmitBean result);
    }

    interface Presenter extends IBasePresenter
    {
        void getRegisterOp();

        void getVerifycode();

        void submitRegister(String username, String password, String verifycode, String tuijian, String qq, String phonenum, String email);
    }
}
