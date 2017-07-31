package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;

/**
 * Description : 用户页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class UserFragment extends BaseFragment implements View.OnClickListener
{

    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;


    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public UserFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_user, container, false);
        mInflater = inflater;
        initView();
        return parentView;
    }

    private void initView()
    {

    }

    @Override
    public void onClick(final View v)
    {
    }
}

