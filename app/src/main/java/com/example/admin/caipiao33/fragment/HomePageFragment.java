package com.example.admin.caipiao33.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.BuyActivity;
import com.example.admin.caipiao33.BuyRecordActivity;
import com.example.admin.caipiao33.BuyRoomActivity;
import com.example.admin.caipiao33.LoginActivity;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.QianDaoActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.RegisterActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.HomePageBean;
import com.example.admin.caipiao33.bean.TokenBean;
import com.example.admin.caipiao33.contract.IHomePageContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.HomePagePresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.LoginEvent;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ResourcesUtil;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.Tools;
import com.example.admin.caipiao33.utils.UserConfig;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.GridView4ScrollView;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.ScrollingTextView;
import com.example.admin.caipiao33.views.banner.ImageCycleViewListener;
import com.example.admin.caipiao33.views.banner.MyBanner;
import com.example.admin.caipiao33.views.banner.MyVerticalBanner;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
public class HomePageFragment extends BaseFragment implements IHomePageContract.View
{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.myBanner)
    MyBanner myBanner;
    @BindView(R.id.tv_scroll)
    ScrollingTextView tvScroll;
    @BindView(R.id.gridView4ScrollView)
    GridView4ScrollView gridView4ScrollView;
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
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.myVerticalBanner)
    MyVerticalBanner myVerticalBanner;
    @BindView(R.id.tv_calc)
    TextView tvCalc;
    @BindView(R.id.iv_homepage_1)
    ImageView ivHomepage1;
    @BindView(R.id.iv_homepage_2)
    ImageView ivHomepage2;
    @BindView(R.id.iv_homepage_3)
    ImageView ivHomepage3;
    @BindView(R.id.iv_homepage_4)
    ImageView ivHomepage4;
    private MainActivity mainActivity;
    private LayoutInflater mInflater;
    private View parentView;
    private IHomePageContract.Presenter mPresenter;
    private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener()
    {
        @Override
        public void displayImage(String imageURL, ImageView imageView)
        {
            if (!imageURL.startsWith("http"))
            {
                imageURL = HttpUtil.mNewUrl + "/" + imageURL;
            }
            MyImageLoader.displayImage(imageURL, imageView, getBaseActivity());
        }

        @Override
        public void onImageClick(int position, View imageView)
        {
            toPromotions();
        }
    };
    private List<HomePageBean.TypeListBean> mTypeListBeanList;
    private MyAdapter adapter;
    private boolean isPause;
    private boolean isvisible;

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
        EventBus.getDefault().register(this);
        return parentView;
    }

    private void initView()
    {
        unbinder = ButterKnife.bind(this, parentView);

        changeToolbar();

        myBanner.setSwipeRefresh(swipeRefreshLayout);
        mLoadingLayout = (LoadingLayout) parentView.findViewById(R.id.loadingLayout);
        mLoadingLayout.setFirtst(true);
        gridView4ScrollView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // 更多彩种
                if (position == parent.getCount() - 1)
                {
                    ((MainActivity) getActivity()).tabSwitchCenter(GouCaiFragment.class);
                    return;
                }
                HomePageBean.TypeListBean typeListBean = mTypeListBeanList.get(position);
                mPresenter.requestRoomData(typeListBean.getNum(), typeListBean.getName());
            }
        });
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
        Tools.setSwipeRefreshColor(swipeRefreshLayout);

        Resources resources = getResources();
        final Drawable homepage1 = resources.getDrawable(R.mipmap.homepage_1);
        final Drawable homepage2 = resources.getDrawable(R.mipmap.homepage_2);
        final Drawable homepage3 = resources.getDrawable(R.mipmap.homepage_3);
        final Drawable homepage4 = resources.getDrawable(R.mipmap.homepage_4);
        ivHomepage1.setImageDrawable(Tools.tintDrawable(homepage1, ColorStateList.valueOf(resources.getColor(R.color.c_homepage_1))));
        ivHomepage2.setImageDrawable(Tools.tintDrawable(homepage2, ColorStateList.valueOf(resources.getColor(R.color.c_homepage_2))));
        ivHomepage3.setImageDrawable(Tools.tintDrawable(homepage3, ColorStateList.valueOf(resources.getColor(R.color.c_homepage_3))));
        ivHomepage4.setImageDrawable(Tools.tintDrawable(homepage4, ColorStateList.valueOf(resources.getColor(R.color.c_homepage_4))));
    }

    private void changeToolbar()
    {
        TokenBean token = UserConfig.getInstance().getToken(getActivity());
        if (null == token || token.getIsLogin() == 0)
        {
            // 未登录
            tvLeft.setBackgroundDrawable(null);
            tvLeft.setText("登录");
            tvRight.setText("注册");
        }
        else
        {
            tvLeft.setText("");
            tvLeft.setBackgroundResource(R.mipmap.user);
            tvRight.setText("签到");
        }
    }

    class MyAdapter extends BaseAdapter
    {

        private List<HomePageBean.TypeListBean> list;

        public MyAdapter(List<HomePageBean.TypeListBean> typeListBeanList)
        {
            this.list = typeListBeanList;
        }

        @Override
        public int getCount()
        {
            if (null == list)
            {
                return 0;
            }
            // 加一表示最后放一个更多彩种
            int total = list.size() + 1;
            // 整除3表示数据是三个三个一行，保证不会出现空缺
            int num = total / 3;
            return num * 3;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (null == convertView)
            {
                convertView = mInflater.inflate(R.layout.item_home_page_hot, null);
            }
            ImageView iv = ViewHolder.get(convertView, R.id.iv);
            TextView tv = ViewHolder.get(convertView, R.id.tv);

            if (position == getCount() - 1)
            {
                MyImageLoader.displayResourceImage(R.mipmap.logo_more_yellow, iv, getBaseActivity());
                tv.setText("更多彩种");
                return convertView;
            }
            if (position >= list.size())
            {
                return convertView;
            }
            HomePageBean.TypeListBean typeListBean = list.get(position);
            if (null == typeListBean)
            {
                return convertView;
            }

            MyImageLoader.displayImage(HttpUtil.mNewUrl + typeListBean.getPic(), iv, getBaseActivity());
            tv.setText(typeListBean.getName());

            return convertView;
        }

        public void updateData(List<HomePageBean.TypeListBean> typeListBeanList)
        {
            if (null != list)
            {
                list.clear();
            }
            this.list = typeListBeanList;
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
        ToastUtil.show(msg);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (isPause)
        {
            isPause = false;
            bannerStart();
        }
        changeToolbar();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        isPause = true;
        bannerStop();
    }

    public void fragmentHide()
    {
        this.isvisible = false;
        bannerStop();
    }

    public void fragmentShow()
    {
        this.isvisible = true;
        bannerStart();
    }

    private void bannerStop()
    {
        if (null != myBanner)
        {
            myBanner.pushImageCycle();
        }
        if (null != myVerticalBanner)
        {
            myVerticalBanner.pushImageCycle();
        }
    }

    private void bannerStart()
    {
        if (!isvisible)
        {
            return;
        }
        if (null != myBanner)
        {
            myBanner.startImageCycle();
        }
        if (null != myVerticalBanner)
        {
            myVerticalBanner.startImageCycle();
        }
    }


    @Override
    public void updateHomePage(HomePageBean bean)
    {
        /**设置数据*/
        myBanner.setImageResources(bean.getScrollImg(), mAdCycleViewListener);
        tvScroll.setText(bean.getScrollNotice());
        tvScroll.setEnabled(true);
        mTypeListBeanList = bean.getTypeList();
        if (null == adapter)
        {
            adapter = new MyAdapter(mTypeListBeanList);
            gridView4ScrollView.setAdapter(adapter);
        }
        else
        {
            adapter.updateData(mTypeListBeanList);
            adapter.notifyDataSetChanged();
        }
        List<HomePageBean.WinListBean> winList = bean.getWinList();
        myVerticalBanner.setNewsData(winList);
        ViewGroup.LayoutParams layoutParams = myVerticalBanner.getLayoutParams();
        if (null != layoutParams)
        {
            layoutParams.height = (tvCalc.getMeasuredHeight() + 2 * ResourcesUtil.getDip(getResources(), R.dimen.d_win_text_padding) + 2 * ResourcesUtil
                    .getDip(getResources(), R.dimen.d_win_text_layout_padding_top)) * 5;
        }

        HomePageBean.PopNoticeBean popNotice = bean.getPopNotice();
        if (null != popNotice)
        {
            // 弹出提示框
            View view = LayoutInflater.from(mainActivity)
                    .inflate(R.layout.item_home_page_tips, null);
            TextView tvTime = (TextView) view.findViewById(R.id.tv_time);
            WebView tvContent = (WebView) view.findViewById(R.id.tv_content);
            tvTime.setText(popNotice.getTime());
            tvContent.loadData(popNotice.getContent(), "text/html; charset=UTF-8", null);
            final String id = popNotice.getId();
            new MaterialDialog.Builder(mainActivity).title(popNotice.getTitle())
                    .customView(view, true)
                    .positiveText("不再提示")
                    .positiveColor(getResources().getColor(R.color.blue))
                    .negativeText("知道了")
                    .onPositive(new MaterialDialog.SingleButtonCallback()
                    {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                        {
                            mPresenter.noTip(id);
                        }
                    })
                    .show();
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
    public void toBuyRoom(BuyRoomBean bean, String title)
    {
        /**
         * 两种情况
         * room
         *  -- 显示房间列表，再次选择一项进入page页面
         * page
         *  -- 立即购买页面
         */

        if (bean.getPage().equals("room"))
        {
            Intent intent = new Intent(getActivity(), BuyRoomActivity.class);
            intent.putExtra(Constants.EXTRA_TITLE, title);
            intent.putExtra(Constants.EXTRA_BUY_ROOM_BEAN, bean);
            startActivity(intent);
        }
        else
        {
            String roomId = bean.getRoomId();
            String playId = null;
            String playId1 = null;
            List<BuyRoomBean.PlayListBean> playList = bean.getPlayList();
            if (null != playList && playList.size() > 0)
            {
                BuyRoomBean.PlayListBean playListBean = playList.get(0);
                playId = playListBean.getPlayId();
                playId1 = playListBean.getPlayId1();
            }
            Intent intent = new Intent(getActivity(), BuyActivity.class);
            intent.putExtra(Constants.EXTRA_TITLE, title);
            intent.putExtra(Constants.EXTRA_NUMBER, bean.getNum());
            intent.putExtra(Constants.EXTRA_ROOM_ID, roomId);
            intent.putExtra(Constants.EXTRA_PLAY_ID, playId);
            intent.putExtra(Constants.EXTRA_PLAY_ID1, playId1);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(LoginEvent messageEvent)
    {
        mPresenter.refreshData();
    }


    @OnClick({R.id.tv_scroll, R.id.ll_func_1, R.id.ll_func_2, R.id.ll_func_3, R.id.ll_func_4, R.id.myVerticalBanner, R.id.tv_left, R.id.tv_right})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_left: // 登录
                // 未登录
                TokenBean token = UserConfig.getInstance().getToken(getActivity());
                if (null == token || token.getIsLogin() == 0)
                {
                    // 未登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    mainActivity.tabSwitchCenter(UserFragment.class);
                }
                break;
            case R.id.tv_right: // 注册  或  签到
                TokenBean token1 = UserConfig.getInstance().getToken(getActivity());
                if (null == token1 || token1.getIsLogin() == 0)
                {
                    // 未登录
                    startActivity(new Intent(getActivity(), RegisterActivity.class));
                }
                else
                {
                    startActivity(new Intent(getActivity(), QianDaoActivity.class));
                }
                break;
            case R.id.tv_scroll: // 滚动的公告
                toWebUrlActivity(HttpUtil.mNewUrl + "/api/systemNotice", "公告");
                break;
            case R.id.ll_func_1: // 存/取款
                TokenBean token3 = UserConfig.getInstance().getToken(getActivity());
                if (null == token3 || token3.getIsLogin() == 0)
                {
                    // 未登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
                }
                else
                {
                    mainActivity.tabSwitchCenter(UserFragment.class);
                    EventBus.getDefault().post(new LoginEvent(""));
                }
                break;
            case R.id.ll_func_2: // 投注记录
                TokenBean token2 = UserConfig.getInstance().getToken(getActivity());
                if (null == token2 || token2.getIsLogin() == 0)
                {
                    // 未登录
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, Constants.REQUEST_CODE_Main2_LOGIN);
                }
                else
                {
                    startActivity(new Intent(getActivity(), BuyRecordActivity.class));
                }
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
}

