package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.contract.IHomePageContract;
import com.example.admin.caipiao33.presenter.HomePagePresenter;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.views.banner.ImageCycleViewListener;
import com.example.admin.caipiao33.views.banner.MyBanner;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Description : 彩票首页
 * Author : cxy
 * Date   : 17/7/31
 */
@SuppressLint("ValidFragment")
public class HomePageFragment extends BaseFragment implements View.OnClickListener, IHomePageContract.View
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.myBanner)
    MyBanner myBanner;
    @BindView(R.id.tv_scroll)
    TextView tvScroll;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.ll_1_1)
    LinearLayout ll11;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.ll_1_2)
    LinearLayout ll12;
    @BindView(R.id.iv_3)
    ImageView iv3;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.ll_1_3)
    LinearLayout ll13;
    @BindView(R.id.ll_1)
    LinearLayout ll1;
    @BindView(R.id.iv_4)
    ImageView iv4;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.ll_2_1)
    LinearLayout ll21;
    @BindView(R.id.iv_5)
    ImageView iv5;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.ll_2_2)
    LinearLayout ll22;
    @BindView(R.id.iv_6)
    ImageView iv6;
    @BindView(R.id.tv_6)
    TextView tv6;
    @BindView(R.id.ll_2_3)
    LinearLayout ll23;
    @BindView(R.id.ll_2)
    LinearLayout ll2;
    @BindView(R.id.iv_7)
    ImageView iv7;
    @BindView(R.id.tv_7)
    TextView tv7;
    @BindView(R.id.ll_3_1)
    LinearLayout ll31;
    @BindView(R.id.iv_8)
    ImageView iv8;
    @BindView(R.id.tv_8)
    TextView tv8;
    @BindView(R.id.ll_3_2)
    LinearLayout ll32;
    @BindView(R.id.iv_9)
    ImageView iv9;
    @BindView(R.id.tv_9)
    TextView tv9;
    @BindView(R.id.ll_3_3)
    LinearLayout ll33;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private IHomePageContract.Presenter mPresenter;
    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener()
    {
        @Override
        public void displayImage(String imageURL, ImageView imageView)
        {
            MyImageLoader.displayImage(imageURL, imageView, getBaseActivity());
        }

        @Override
        public void onImageClick(int position, View imageView)
        {

        }
    };
    private HomePageBean mHomePageBean;


    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public HomePageFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mainActivity = (MainActivity) getActivity();
        parentView = inflater.inflate(R.layout.fragment_homepage, container, false);
        mInflater = inflater;
        mPresenter = new HomePagePresenter(this);
        initView();
        mPresenter.loadData();
        return parentView;
    }

    private void initView()
    {
        unbinder = ButterKnife.bind(this, parentView);
        myBanner.setSwipeRefresh(swipeRefreshLayout);
    }

    @Override
    public void onClick(final View v)
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
    public void updateHomePage(HomePageBean bean)
    {
//        this.mHomePageBean = bean;
//        /**设置数据*/
//        myBanner.setImageResources(bean.getScrollImg(), mAdCycleViewListener);
//        ArrayList<String> urls = new ArrayList<>();
//        for (HomePageBean.BannerBean bean : bean.getBanner())
//        {
//            urls.add(bean.getImage_url());
//        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_scroll, R.id.ll_1_1, R.id.ll_1_2, R.id.ll_1_3, R.id.ll_2_1, R.id.ll_2_2, R.id.ll_2_3, R.id.ll_3_1, R.id.ll_3_2, R.id.ll_3_3})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_scroll:
                break;
            case R.id.ll_1_1:
                break;
            case R.id.ll_1_2:
                break;
            case R.id.ll_1_3:
                break;
            case R.id.ll_2_1:
                break;
            case R.id.ll_2_2:
                break;
            case R.id.ll_2_3:
                break;
            case R.id.ll_3_1:
                break;
            case R.id.ll_3_2:
                break;
            case R.id.ll_3_3:
                break;
        }
    }
}

