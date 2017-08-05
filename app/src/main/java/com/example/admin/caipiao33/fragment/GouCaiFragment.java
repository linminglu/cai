package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IGouCaiContract;
import com.example.admin.caipiao33.presenter.GouCaiPresenter;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description : 购彩页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class GouCaiFragment extends BaseFragment implements IGouCaiContract.View, RadioGroup.OnCheckedChangeListener
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.goucai_all)
    RadioButton goucaiAll;
    @BindView(R.id.goucai_gp)
    RadioButton goucaiGp;
    @BindView(R.id.goucai_dp)
    RadioButton goucaiDp;
    @BindView(R.id.goucai_radioGroup)
    RadioGroup goucaiRadioGroup;
    @BindView(R.id.goucai_pager)
    ViewPager goucaiPager;
    Unbinder unbinder;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private IGouCaiContract.Presenter mPresenter;
    private GouCaiItemFragment fragmentAll;
    private GouCaiItemFragment fragmentGP;
    private GouCaiItemFragment fragmentDP;
    private GouCaiBean mGouCaiBeant;
    public boolean isLinearLayout = true;

    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public GouCaiFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_goucai, container, false);
        mInflater = inflater;
        initView();
        mPresenter = new GouCaiPresenter(this);
        mPresenter.loadData();
        return parentView;
    }

    private void initView()
    {
        unbinder = ButterKnife.bind(this, parentView);

        toolbar.inflateMenu(R.menu.menu_goucai);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.action_switch:
                        if (isLinearLayout) {
                            item.setIcon(R.mipmap.goucai_list);
                        } else {
                            item.setIcon(R.mipmap.goucai_gride);
                        }
                        isLinearLayout = !isLinearLayout;
                        int currentItem = goucaiPager.getCurrentItem();
                        if (currentItem == 0) {
                            fragmentAll.updateUILayout();
                        } else if (currentItem == 1) {
                            fragmentGP.updateUILayout();
                        } else {
                            fragmentDP.updateUILayout();
                        }
                        break;
                }
                return false;
            }
        });
        mLoadingLayout = (LoadingLayout) parentView.findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.loadData();
            }
        });
        goucaiRadioGroup.setOnCheckedChangeListener(this);
        fragmentAll = GouCaiItemFragment.newInstance(GouCaiItemFragment.TYPE_ALL);
        fragmentGP = GouCaiItemFragment.newInstance(GouCaiItemFragment.TYPE_GP);
        fragmentDP = GouCaiItemFragment.newInstance(GouCaiItemFragment.TYPE_DP);
        goucaiPager.setAdapter(new GouCaiPagerAdapter(getChildFragmentManager()));
        goucaiPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                if (position == 0)
                {
                    goucaiAll.setChecked(true);
                }
                else if (position == 1)
                {
                    goucaiGp.setChecked(true);
                }
                else
                {
                    goucaiDp.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    private class GouCaiPagerAdapter extends FragmentPagerAdapter
    {

        public GouCaiPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            if (position == 0) {
                return fragmentAll;
            } else if (position == 1) {
                return fragmentGP;
            } else {
                return fragmentDP;
            }
        }

        @Override
        public int getCount()
        {
            return 3;
        }

    }

    @Override
    public BaseActivity getBaseActivity()
    {
        return (MainActivity) getActivity();
    }

    @Override
    public void showErrorMsg(String msg)
    {

    }

    @Override
    public void updateHomePage(GouCaiBean bean)
    {
        this.mGouCaiBeant = bean;
        int currentItem = goucaiPager.getCurrentItem();
        fragmentAll.setGouCaiBean(bean);
        fragmentGP.setGouCaiBean(bean);
        fragmentDP.setGouCaiBean(bean);
        if (currentItem == 0) {
            fragmentAll.refreshRecyclerView();
        } else if (currentItem == 1) {
            fragmentGP.refreshRecyclerView();
        } else {
            fragmentDP.refreshRecyclerView();
        }
    }

    @Override
    public void hideRefreshing()
    {
        int currentItem = goucaiPager.getCurrentItem();
        if (currentItem == 0) {
            fragmentAll.hideRefreshing();
        } else if (currentItem == 1) {
            fragmentGP.hideRefreshing();
        } else {
            fragmentDP.hideRefreshing();
        }
    }

    public void toRefreshData() {
        mPresenter.refreshData();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
    {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.goucai_all:
                goucaiPager.setCurrentItem(0);
                break;
            case R.id.goucai_gp:
                goucaiPager.setCurrentItem(1);
                break;
            case R.id.goucai_dp:
                goucaiPager.setCurrentItem(2);
                break;
        }
    }
}

