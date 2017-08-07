package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.PromotionsActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.contract.IHomePageContract;
import com.example.admin.caipiao33.encryption.CreateCode;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.HomePagePresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.P2PNative;
import com.example.admin.caipiao33.utils.ResourcesUtil;
import com.example.admin.caipiao33.utils.Tools;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.banner.ImageCycleViewListener;
import com.example.admin.caipiao33.views.banner.MyBanner;
import com.example.admin.caipiao33.views.banner.MyVerticalBanner;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.iv_9)
    ImageView iv9;
    @BindView(R.id.ll_3_2)
    LinearLayout ll32;
    @BindView(R.id.ll_3_3)
    LinearLayout ll33;
    @BindView(R.id.ll_3)
    LinearLayout ll3;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.ll_func_1)
    LinearLayout llFunc1;
    @BindView(R.id.ll_func_2)
    LinearLayout llFunc2;
    @BindView(R.id.ll_func_3)
    LinearLayout llFunc3;
    @BindView(R.id.ll_func_4)
    LinearLayout llFunc4;
    @BindView(R.id.tv_9)
    TextView tv9;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.myVerticalBanner)
    MyVerticalBanner myVerticalBanner;
    @BindView(R.id.tv_calc)
    TextView tvCalc;
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
            toPromotions();
        }
    };
    private List<ViewHolder> mHotType;

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

        String s = "ttEB8PraOcdVpEUpR0Acpcf7aAAJvrqQWz/vd14MtKHwLxCWybD/Iaa78EUfxvRmocqOt8xDHX5ZV5XVgFU1l99LZj4ft9lhgmUz70OOO+M=";
        KLog.e("asdfasdf", P2PNative.getInstance().decrypt(s));
        return parentView;
    }

    private void initView()
    {
        unbinder = ButterKnife.bind(this, parentView);
        myBanner.setSwipeRefresh(swipeRefreshLayout);
        mLoadingLayout = (LoadingLayout) parentView.findViewById(R.id.loadingLayout);
        mHotType = new ArrayList<>();
        mHotType.add(new ViewHolder(iv1, tv1));
        mHotType.add(new ViewHolder(iv2, tv2));
        mHotType.add(new ViewHolder(iv3, tv3));
        mHotType.add(new ViewHolder(iv4, tv4));
        mHotType.add(new ViewHolder(iv5, tv5));
        mHotType.add(new ViewHolder(iv6, tv6));
        mHotType.add(new ViewHolder(iv7, tv7));
        mHotType.add(new ViewHolder(iv8, tv8));
        MyImageLoader.displayResourceImage(R.mipmap.logo_more_yellow, iv9, getBaseActivity());
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
        return (MainActivity) getActivity();
    }

    @Override
    public void showErrorMsg(String msg)
    {

    }

    @Override
    public void updateHomePage(HomePageBean bean)
    {
        /**设置数据*/
        myBanner.setImageResources(bean.getScrollImg(), mAdCycleViewListener);
        tvScroll.setText(bean.getScrollNotice());
        for (int i = 0; i < bean.getTypeList().size(); i++)
        {
            HomePageBean.TypeListBean typeListBean = bean.getTypeList().get(i);
            ViewHolder viewHolder = mHotType.get(i);
            MyImageLoader.displayImage(HttpUtil.mNewUrl + typeListBean.getPic(), viewHolder.iv, getBaseActivity());
            viewHolder.tv.setText(typeListBean.getName());
        }
        List<HomePageBean.WinListBean> winList = bean.getWinList();
        myVerticalBanner.setNewsData(winList);
        ViewGroup.LayoutParams layoutParams = myVerticalBanner.getLayoutParams();
        if (null != layoutParams)
        {
            layoutParams.height = (tvCalc.getMeasuredHeight() + 2 * ResourcesUtil.getDip(getResources(), R.dimen.d_win_text_padding) + 2 * ResourcesUtil
                    .getDip(getResources(), R.dimen.d_win_text_layout_padding_top)) * 5;
        }
    }

    @Override
    public void hideRefreshing()
    {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateServiceUrl(String url)
    {
        toWebUrlActivity(url, "在线客服");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_scroll, R.id.ll_1_1, R.id.ll_1_2, R.id.ll_1_3, R.id.ll_2_1, R.id.ll_2_2, R.id.ll_2_3, R.id.ll_3_1, R.id.ll_3_2, R.id.ll_3_3, R.id.ll_func_1, R.id.ll_func_2, R.id.ll_func_3, R.id.ll_func_4, R.id.myVerticalBanner})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_scroll: // 滚动的公告
                toWebUrlActivity(HttpUtil.mNewUrl + "/api/systemNotice", "公告");
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
            case R.id.ll_3_3: // 更多彩种
                ((MainActivity) getActivity()).tabSwitchCenter(GouCaiFragment.class);
                break;
            case R.id.ll_func_1: // 存/取款
                break;
            case R.id.ll_func_2: // 投注记录
                break;
            case R.id.ll_func_3: // 优惠活动
                toPromotions();
                break;
            case R.id.ll_func_4: // 在线客服
                mPresenter.toAskService();
                break;
            case R.id.myVerticalBanner:
                toWebUrlActivity(HttpUtil.mNewUrl + "/api/win/list", "最新中奖榜");
                break;
        }
    }

    // 跳转到网页
    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(getActivity(), WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }

    // 跳转到优惠活动
    private void toPromotions()
    {
        toWebUrlActivity(HttpUtil.mNewUrl + "/api/activity", "优惠活动");
        //        startActivity(new Intent(getActivity(), PromotionsActivity.class));
    }

    private class ViewHolder
    {
        ImageView iv;
        TextView tv;

        public ViewHolder(ImageView iv, TextView tv)
        {
            this.iv = iv;
            this.tv = tv;
        }
    }
}

