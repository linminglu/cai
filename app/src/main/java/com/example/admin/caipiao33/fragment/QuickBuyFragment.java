package com.example.admin.caipiao33.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;

/**
 * Description : 走势页面
 * Author : cxy
 * Date   : 17/7/31
 */
public class QuickBuyFragment extends BaseFragment implements View.OnClickListener
{

    private LayoutInflater mInflater;
    private View parentView;


    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public QuickBuyFragment()
    {
    }

    /**
     */
    public static QuickBuyFragment newInstance()
    {
        QuickBuyFragment fragment = new QuickBuyFragment();
        Bundle args = new Bundle();
//        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
//            mType = getArguments().getInt(TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        parentView = inflater.inflate(R.layout.fragment_quick_buy, container, false);
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

