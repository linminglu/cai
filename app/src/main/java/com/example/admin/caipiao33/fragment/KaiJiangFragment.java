package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.KaiJiangDTBean;
import com.example.admin.caipiao33.contract.IKaiJiangContract;
import com.example.admin.caipiao33.fragment.adapter.KaiJiangAdapter;
import com.example.admin.caipiao33.presenter.KaiJiangDTPresenter;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description : 开奖页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class KaiJiangFragment extends BaseFragment implements View.OnClickListener, IKaiJiangContract.View
{
    @BindView(R.id.kaijiang_lv)
    ListView kaijiangLv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private IKaiJiangContract.Presenter mPresenter;
    private KaiJiangAdapter kaiJiangAdapter;

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
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        mPresenter = new KaiJiangDTPresenter(this, swipeRefreshLayout);
        mPresenter.loadData();
        return parentView;
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) parentView.findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.loadData();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mPresenter.refreshData();
            }
        });
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
        ToastUtil.show(msg);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void update(ArrayList<KaiJiangDTBean> bean)
    {
        if (kaiJiangAdapter == null)
        {
            kaiJiangAdapter = new KaiJiangAdapter(bean, mInflater, kaijiangLv, mainActivity);
        }
        else
        {
            kaiJiangAdapter.setBeanContents(bean);
        }
        kaiJiangAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

