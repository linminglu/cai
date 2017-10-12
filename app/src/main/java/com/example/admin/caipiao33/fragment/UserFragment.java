package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.BuyRecordActivity;
import com.example.admin.caipiao33.BuyWinRecordActivity;
import com.example.admin.caipiao33.ChongZhiJiLuActivity;
import com.example.admin.caipiao33.GeRenXiaoXiActivity;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.QianDaoActivity;
import com.example.admin.caipiao33.QianDaoJiLuActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.SettingActivity;
import com.example.admin.caipiao33.TiKuanJiLuActivity;
import com.example.admin.caipiao33.TiXianActivity;
import com.example.admin.caipiao33.TopupActivity;
import com.example.admin.caipiao33.TuiJianActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.ZhangHuMingXiActivity;
import com.example.admin.caipiao33.bean.UserInfoBean;
import com.example.admin.caipiao33.contract.IUserContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.presenter.UserInfoPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.LoginEvent;
import com.example.admin.caipiao33.utils.UserConfig;
import com.example.admin.caipiao33.views.CircleImageView;
import com.example.admin.caipiao33.views.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.admin.caipiao33.R.id.toolbar;


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
    @BindView(R.id.user_fragment_app_tv)
    TextView user_fragmentAppTv;
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
    @BindView(R.id.user_fragment_tuijian_rl)
    RelativeLayout userFragmentTuijianRl;
    @BindView(R.id.user_fragment_gonggao_iv)
    ImageView userFragmentGonggaoIv;
    @BindView(R.id.user_fragment_gonggao_rl)
    RelativeLayout userFragmentGonggaoRl;
    @BindView(R.id.user_fragment_touzhujilu_iv)
    ImageView userFragmentTouzhujiluIv;
    @BindView(R.id.user_fragment_touzhujilu_rl)
    RelativeLayout userFragmentTouzhujiluRl;
    @BindView(R.id.user_fragment_zhongjiangjilu_iv)
    ImageView userFragmentZhongjiangjiluIv;
    @BindView(R.id.user_fragment_zhongjiangjilu_rl)
    RelativeLayout userFragmentZhongjiangjiluRl;
    @BindView(R.id.user_fragment_mingxi_iv)
    ImageView userFragmentMingxiIv;
    @BindView(R.id.user_fragment_mingxi_rl)
    RelativeLayout userFragmentMingxiRl;
    @BindView(R.id.user_fragment_chongzhijilu_iv)
    ImageView userFragmentChongzhijiluIv;
    @BindView(R.id.user_fragment_chongzhijilu_rl)
    RelativeLayout userFragmentChongzhijiluRl;
    @BindView(R.id.user_fragment_tikuanjilu_iv)
    ImageView userFragmentTikuanjiluIv;
    @BindView(R.id.user_fragment_tikuanjilu_rl)
    RelativeLayout userFragmentTikuanjiluRl;
    @BindView(R.id.user_fragment_qiandaojilu_iv)
    ImageView userFragmentQiandaojiluIv;
    @BindView(R.id.user_fragment_qiandaojilu_rl)
    RelativeLayout userFragmentQiandaojiluRl;
    @BindView(R.id.user_fragment_geren_iv)
    ImageView userFragmentGerenIv;
    @BindView(R.id.user_fragment_geren_rl)
    RelativeLayout userFragmentGerenRl;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.user_fragment_sv)
    ScrollView userFragmentSv;
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
        EventBus.getDefault().register(this);
        return parentView;
    }

    private void initView()
    {
        mToolbar = (Toolbar) parentView.findViewById(toolbar);
        mToolbar.inflateMenu(R.menu.menu_userinfo);
        mToolbar.setOnMenuItemClickListener(this);
        mLoadingLayout = (LoadingLayout) parentView.findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mLoadingLayout.setOnStartLoading(null);
                mPresenter.loadData();
            }
        });
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(mainActivity, null, android.support.v7.appcompat.R.styleable.ActionBar, android.support.v7.appcompat.R.attr.actionBarStyle, 0);
        Drawable drawable = a.getDrawable(android.support.v7.appcompat.R.styleable.ActionBar_homeAsUpIndicator);
        drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
        mToolbar.setNavigationIcon(drawable);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mainActivity.tabSwitchCenter(HomePageFragment.class);
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
        switch (item.getItemId())
        {
            case R.id.action_message:
                Intent intent = new Intent(mainActivity, SettingActivity.class);
                startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGOUT);
                break;
        }
        return false;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
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
        userFragmentYueTv.setText("余额[刷新]\n" + bean.getBalance() + "元");
    }

    @OnClick({R.id.user_fragment_app_tv, R.id.user_fragment_yue_tv, R.id.user_fragment_tixian_tv, R.id.user_fragment_kefu_tv, R.id.user_fragment_chongzhi_tv, R.id.user_fragment_qiandao_tv, R.id.user_fragment_tuijian_rl, R.id.user_fragment_gonggao_rl, R.id.user_fragment_touzhujilu_rl, R.id.user_fragment_zhongjiangjilu_rl, R.id.user_fragment_mingxi_rl, R.id.user_fragment_chongzhijilu_rl, R.id.user_fragment_tikuanjilu_rl, R.id.user_fragment_qiandaojilu_rl, R.id.user_fragment_geren_rl})
    public void onViewClicked(View view)
    {
        Intent intent;
        switch (view.getId())
        {

            case R.id.user_fragment_yue_tv:
                mPresenter.refreshData();
                break;
            case R.id.user_fragment_app_tv:
                toWebUrlActivity("https://m.cp89003.com/common/app/down", "手机APP");
                break;
            case R.id.user_fragment_chongzhi_tv:
                intent = new Intent(mainActivity, TopupActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_qiandao_tv:
                intent = new Intent(mainActivity, QianDaoActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_tixian_tv:
                intent = new Intent(mainActivity, TiXianActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_kefu_tv:
                showLoadingDialog(false);
                HttpUtil.requestFirst("kefu", String.class, mainActivity, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        toWebUrlActivity(result, "在线客服");
                    }

                    @Override
                    public void onFailed(int code, String msg)
                    {

                    }

                    @Override
                    public void onFinish()
                    {
                        hideLoadingDialog();
                    }
                }, null);
                break;
            case R.id.user_fragment_tuijian_rl:
                intent = new Intent(mainActivity, TuiJianActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_gonggao_rl:
                toWebUrlActivity(HttpUtil.mNewUrl + "/api/systemNotice", "公告");
                break;
            case R.id.user_fragment_touzhujilu_rl:
                intent = new Intent(mainActivity, BuyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_zhongjiangjilu_rl:
                intent = new Intent(mainActivity, BuyWinRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_mingxi_rl:
                intent = new Intent(mainActivity, ZhangHuMingXiActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_chongzhijilu_rl:
                intent = new Intent(mainActivity, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_tikuanjilu_rl:
                intent = new Intent(mainActivity, TiKuanJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_qiandaojilu_rl:
                intent = new Intent(mainActivity, QianDaoJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.user_fragment_geren_rl:
                intent = new Intent(mainActivity, GeRenXiaoXiActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(LoginEvent messageEvent)
    {
        userFragmentSv.fullScroll(ScrollView.FOCUS_UP);
        mPresenter.loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 跳转到网页
    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(mainActivity, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }
}

