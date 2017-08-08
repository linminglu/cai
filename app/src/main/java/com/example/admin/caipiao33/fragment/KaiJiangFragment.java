package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.KaiJiangDTBean;
import com.example.admin.caipiao33.contract.IKaiJiangContract;
import com.example.admin.caipiao33.contract.IUserContract;
import com.example.admin.caipiao33.presenter.KaiJiangDTPresenter;
import com.example.admin.caipiao33.presenter.UserInfoPresenter;

import java.util.ArrayList;

/**
 * Description : 开奖页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class KaiJiangFragment extends BaseFragment implements View.OnClickListener, IKaiJiangContract.View
{

    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private IKaiJiangContract.Presenter mPresenter;

    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public KaiJiangFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_kaijiang, container, false);
        mInflater = inflater;
        initView();
        mPresenter = new KaiJiangDTPresenter(this, null);
        mPresenter.loadData();
        return parentView;
    }

    private void initView()
    {

    }

    @Override
    public void onClick(final View v)
    {
    }

    @Override
    public BaseActivity getBaseActivity()
    {
        return null;
    }

    @Override
    public void showErrorMsg(String msg)
    {

    }

    @Override
    public void update(ArrayList<KaiJiangDTBean> bean)
    {

    }
}

