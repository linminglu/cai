package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.bean.UserInfoBean;
import com.example.admin.caipiao33.contract.IUserContract;
import com.example.admin.caipiao33.presenter.UserInfoPresenter;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;
import com.example.admin.caipiao33.views.CircleImageView;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description : 用户页面
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class UserFragment extends BaseFragment implements View.OnClickListener, Toolbar.OnMenuItemClickListener, IUserContract.View
{

    @BindView(R.id.user_fragment_icon_iv)
    CircleImageView userFragmentIconIv;
    @BindView(R.id.user_fragment_name_tv)
    TextView userFragmentNameTv;
    @BindView(R.id.user_fragment_yue_tv)
    TextView userFragmentYueTv;
    @BindView(R.id.user_fragment_kefu_tv)
    TextView userFragmentKefuTv;
    @BindView(R.id.user_fragment_chongzhi_tv)
    TextView userFragmentChongzhiTv;
    @BindView(R.id.user_fragment_tixian_tv)
    TextView userFragmentTixianTv;
    @BindView(R.id.user_fragment_qiandao_tv)
    TextView userFragmentQiandaoTv;
    @BindView(R.id.user_fragment_tuijian_iv)
    ImageView userFragmentTuijianIv;
    @BindView(R.id.user_fragment_tuijian_tv)
    TextView userFragmentTuijianTv;
    @BindView(R.id.user_fragment_gonggao_iv)
    ImageView userFragmentGonggaoIv;
    @BindView(R.id.minefragment_gonggao_tv)
    TextView minefragmentGonggaoTv;
    @BindView(R.id.user_fragment_touzhu_iv)
    ImageView userFragmentTouzhuIv;
    @BindView(R.id.minefragment_touzhu_tv)
    TextView minefragmentTouzhuTv;
    @BindView(R.id.user_fragment_zhongjiang_iv)
    ImageView userFragmentZhongjiangIv;
    @BindView(R.id.minefragment_zhongjiang_tv)
    TextView minefragmentZhongjiangTv;
    @BindView(R.id.user_fragment_mingxi_iv)
    ImageView userFragmentMingxiIv;
    @BindView(R.id.minefragment_mingxi_tv)
    TextView minefragmentMingxiTv;
    @BindView(R.id.user_fragment_chongzhijilu_iv)
    ImageView userFragmentChongzhijiluIv;
    @BindView(R.id.minefragment_chongzhijilu_tv)
    TextView minefragmentChongzhijiluTv;
    @BindView(R.id.user_fragment_tikuanjilu_iv)
    ImageView userFragmentTikuanjiluIv;
    @BindView(R.id.minefragment_tikuanjilu_tv)
    TextView minefragmentTikuanjiluTv;
    @BindView(R.id.user_fragment_qiandaojilu_iv)
    ImageView userFragmentQiandaojiluIv;
    @BindView(R.id.minefragment_qiandao_tv)
    TextView minefragmentQiandaoTv;
    @BindView(R.id.user_fragment_geren_iv)
    ImageView userFragmentGerenIv;
    @BindView(R.id.minefragment_geren_tv)
    TextView minefragmentGerenTv;
    Unbinder unbinder;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private Toolbar mToolbar;
    private IUserContract.Presenter mPresenter;


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
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        mPresenter = new UserInfoPresenter(this, swipeRefreshLayout);
        mPresenter.loadData();
        return parentView;
    }

    private void initView()
    {
        mToolbar = (Toolbar) parentView.findViewById(R.id.toolbar);
        mToolbar.inflateMenu(R.menu.menu_userinfo);
        mToolbar.setOnMenuItemClickListener(this);
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
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.user_fragment_icon_iv, R.id.user_fragment_name_tv, R.id.user_fragment_yue_tv, R.id.user_fragment_kefu_tv, R.id.user_fragment_chongzhi_tv, R.id.user_fragment_tixian_tv, R.id.user_fragment_qiandao_tv, R.id.user_fragment_tuijian_iv, R.id.user_fragment_tuijian_tv, R.id.user_fragment_gonggao_iv, R.id.minefragment_gonggao_tv, R.id.user_fragment_touzhu_iv, R.id.minefragment_touzhu_tv, R.id.user_fragment_zhongjiang_iv, R.id.minefragment_zhongjiang_tv, R.id.user_fragment_mingxi_iv, R.id.minefragment_mingxi_tv, R.id.user_fragment_chongzhijilu_iv, R.id.minefragment_chongzhijilu_tv, R.id.user_fragment_tikuanjilu_iv, R.id.minefragment_tikuanjilu_tv, R.id.user_fragment_qiandaojilu_iv, R.id.minefragment_qiandao_tv, R.id.user_fragment_geren_iv, R.id.minefragment_geren_tv})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.user_fragment_icon_iv:
                ToastUtil.show("123");
                break;
            case R.id.user_fragment_name_tv:
                break;
            case R.id.user_fragment_yue_tv:
                break;
            case R.id.user_fragment_kefu_tv:
                break;
            case R.id.user_fragment_chongzhi_tv:
                break;
            case R.id.user_fragment_tixian_tv:
                break;
            case R.id.user_fragment_qiandao_tv:
                break;
            case R.id.user_fragment_tuijian_iv:
                break;
            case R.id.user_fragment_tuijian_tv:
                break;
            case R.id.user_fragment_gonggao_iv:
                break;
            case R.id.minefragment_gonggao_tv:
                break;
            case R.id.user_fragment_touzhu_iv:
                break;
            case R.id.minefragment_touzhu_tv:
                break;
            case R.id.user_fragment_zhongjiang_iv:
                break;
            case R.id.minefragment_zhongjiang_tv:
                break;
            case R.id.user_fragment_mingxi_iv:
                break;
            case R.id.minefragment_mingxi_tv:
                break;
            case R.id.user_fragment_chongzhijilu_iv:
                break;
            case R.id.minefragment_chongzhijilu_tv:
                break;
            case R.id.user_fragment_tikuanjilu_iv:
                break;
            case R.id.minefragment_tikuanjilu_tv:
                break;
            case R.id.user_fragment_qiandaojilu_iv:
                break;
            case R.id.minefragment_qiandao_tv:
                break;
            case R.id.user_fragment_geren_iv:
                break;
            case R.id.minefragment_geren_tv:
                break;
        }
    }

    @Override
    public void onClick(View v)
    {

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
    public void updateUsers(UserInfoBean bean)
    {
        swipeRefreshLayout.setRefreshing(false);
        userFragmentNameTv.setText(UserConfig.getInstance().getToken(mainActivity).getMemberName());
        userFragmentYueTv.setText(bean.getBalance() + "元");
    }
}

