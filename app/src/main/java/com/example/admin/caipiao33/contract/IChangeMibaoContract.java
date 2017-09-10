package com.example.admin.caipiao33.contract;

import com.example.admin.caipiao33.IBasePresenter;
import com.example.admin.caipiao33.IBaseView;

/**
 * Created by cxy on 2017/9/10
 */

public interface IChangeMibaoContract
{
    interface View extends IBaseView
    {
        void successFul(String result);

        void updata(String result);
    }

    interface Presenter extends IBasePresenter
    {
        void changeMibao(String question, String answer, String nAnswer);

        void setMibao(String question, String answer);

        void getMibao();
    }
}
