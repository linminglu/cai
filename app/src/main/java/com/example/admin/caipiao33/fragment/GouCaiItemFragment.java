package com.example.admin.caipiao33.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.BuyActivity;
import com.example.admin.caipiao33.BuyRoomActivity;
import com.example.admin.caipiao33.LazyFragment;
import com.example.admin.caipiao33.MainActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.contract.IGouCaiItemContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.GouCaiItemPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.DateUtils;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.ServiceTime;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.Tools;
import com.example.admin.caipiao33.views.DividerGridItemDecoration;
import com.example.admin.caipiao33.views.DividerItemDecoration;
import com.example.admin.caipiao33.views.ResultAssist;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 购彩子项目
 * 全部彩种、高频彩、低频彩
 */
public class GouCaiItemFragment extends LazyFragment implements IGouCaiItemContract.View
{
    public static final int TYPE_ALL = 0; // 全部彩种
    public static final int TYPE_GP = 1; // 高频彩
    public static final int TYPE_DP = 2; // 低频彩

    private static final String TYPE = "param1";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    Unbinder unbinder;
    private List<GouCaiBean.DataBean> mDataList;
    private int mType;
    private MyGouCaiAdapter adapter;
    private boolean isCreate;
    private GouCaiBean mGouCaiBean;
    private MyGouCaiGrideAdapter grideAdapter;
    private boolean isPause;
    private DividerItemDecoration lineDecor;
    private DividerGridItemDecoration gridDecor;

    public GouCaiItemFragment()
    {
        // Required empty public constructor
    }

    /**
     * @param type 类型
     */
    public static GouCaiItemFragment newInstance(int type)
    {
        GouCaiItemFragment fragment = new GouCaiItemFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mType = getArguments().getInt(TYPE);
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback()
    {
        @Override
        public boolean handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case WHAT_REFRESH_ITEM:
                    String obj = (String) msg.obj; // 彩票编码
                    if (refreshList.contains(obj))
                    {
                        // 表示有内容正在刷新，不用再次请求网络
                        break;
                    }
                    synchronized (refreshList)
                    {
                        refreshList.add(obj);
                    }
                    mPresenter.refreshData(obj, WHAT_REFRESH_ITEM);
                    break;
                case WHAT_REFRESH_RESULT:
                    String num = (String) msg.obj; // 彩票编码
                    if (refreshList.contains(num))
                    {
                        // 表示有内容正在刷新，不用再次请求网络
                        break;
                    }
                    synchronized (refreshList)
                    {
                        refreshList.add(num);
                    }
                    mPresenter.refreshData(num, WHAT_REFRESH_RESULT);
                    break;
            }
            return false;
        }
    });

    private static final int LIMIT_TIME = 1000; // 定时器1秒刷新页面
    private static final int LOOP_TIME = 5; // 定时每隔5秒请求一次开奖结果
    private static final int WHAT_REFRESH_ITEM = 101; // 用于刷新单个项目
    private static final int WHAT_REFRESH_RESULT = 102; // 用于刷新开奖结果

    // 存储请求刷新数据的列表
    private List<String> refreshList = new ArrayList<>();
    private IGouCaiItemContract.Presenter mPresenter;
    private Runnable timerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            long l = System.currentTimeMillis();
            GouCaiFragment fragment = (GouCaiFragment) getParentFragment();
            if (!fragment.isLinearLayout || !isVisible)
            {
                mHandler.removeCallbacks(this);
                return;
            }
            // 页面不在滑动状态才更新
            if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE)
            {
                adapter.notifyDataSetChanged();
                KLog.e("timerRunnable");
            }
            long temp = System.currentTimeMillis() - l;
            mHandler.postDelayed(this, LIMIT_TIME - temp);
        }
    };

    @Override
    public void onResume()
    {
        KLog.e("GouCaiItemFragment onResume");
        super.onResume();
        timerResume();
    }

    public void timerResume()
    {
        GouCaiFragment pf = (GouCaiFragment) getParentFragment();
        if (!pf.isvisible)
        {
            return;
        }
        if (isPause)
        {
            // 页面暂停过，需要重新加载
            isPause = false;
            if (isVisible)
            {
                swipeRefreshLayout.setRefreshing(true);
                GouCaiFragment fragment = (GouCaiFragment) getParentFragment();
                fragment.toRefreshData();
            }
        }
    }

    @Override
    public void onPause()
    {
        KLog.e("GouCaiItemFragment onPause");
        super.onPause();
        timerPause();
    }

    public void timerPause()
    {
        this.isPause = true;
        mHandler.removeCallbacks(timerRunnable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_gou_cai_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        Tools.setSwipeRefreshColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                GouCaiFragment fragment = (GouCaiFragment) getParentFragment();
                fragment.toRefreshData();
            }
        });
        lineDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        gridDecor = new DividerGridItemDecoration(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(lineDecor);
        adapter = new MyGouCaiAdapter();
        grideAdapter = new MyGouCaiGrideAdapter();
        recyclerView.setAdapter(adapter);
        isCreate = true;
        lazyLoad();
        mPresenter = new GouCaiItemPresenter(this);
        return view;
    }

    public void hideRefreshing()
    {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
    }

    public void setGouCaiBean(GouCaiBean bean)
    {
        this.mGouCaiBean = bean;
    }

    public void refreshRecyclerView()
    {
        if (null == mGouCaiBean)
        {
            return;
        }
        if (mType == TYPE_ALL)
        {
            mDataList = mGouCaiBean.getAll();
        }
        else if (mType == TYPE_GP)
        {
            mDataList = mGouCaiBean.getGpc();
        }
        else
        {
            mDataList = mGouCaiBean.getDpc();
        }
        GouCaiFragment fragment = (GouCaiFragment) getParentFragment();
        if (fragment.isLinearLayout)
        {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && adapter instanceof MyGouCaiAdapter)
            {
                adapter.notifyDataSetChanged();
            }
            else
            {
                updateUILayout();
            }
            mHandler.removeCallbacks(timerRunnable);
            mHandler.post(timerRunnable);
        }
        else
        {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && adapter instanceof MyGouCaiGrideAdapter)
            {
                adapter.notifyDataSetChanged();
            }
            else
            {
                updateUILayout();
            }
        }
    }

    public void updateUILayout()
    {
        GouCaiFragment fragment = (GouCaiFragment) getParentFragment();
        if (fragment.isLinearLayout)
        {
            mHandler.removeCallbacks(timerRunnable);
            mHandler.post(timerRunnable);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.removeItemDecoration(gridDecor);
            recyclerView.addItemDecoration(lineDecor);
            recyclerView.setAdapter(adapter);
        }
        else
        {
            recyclerView.removeItemDecoration(lineDecor);
            recyclerView.addItemDecoration(gridDecor);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(grideAdapter);
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        isCreate = false;
        unbinder.unbind();
    }

    @Override
    protected void lazyLoad()
    {
        if (!isCreate || !isVisible)
        {
            return;
        }
        refreshRecyclerView();
    }

    @Override
    public BaseActivity getBaseActivity()
    {
        return (MainActivity) getParentFragment().getActivity();
    }

    @Override
    public void showErrorMsg(String msg)
    {
        ToastUtil.show(msg);
    }

    @Override
    public void updateItem(GouCaiBean.DataBean bean, int what)
    {
        String num = bean.getNum();
        if (what == WHAT_REFRESH_RESULT)
        {
            String lastOpen = bean.getLastOpen();
            if (StringUtils.isEmpty(lastOpen))
            {
                if (refreshList.contains(num))
                {
                    synchronized (refreshList)
                    {
                        refreshList.remove(num);
                    }
                }
                return;
            }
        }
        for (int i = 0; i < mDataList.size(); i++)
        {
            GouCaiBean.DataBean item = mDataList.get(i);
            if (item.getNum().equals(num))
            {
                changeItemDataNotify(i, item, bean);
                break;
            }
        }
    }

    @Override
    public void refreshDataFailed(String num, int what)
    {
        synchronized (refreshList)
        {
            refreshList.remove(num);
        }
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

    private void changeItemDataNotify(int position, GouCaiBean.DataBean item, GouCaiBean.DataBean newData)
    {
        item.setEndTime(newData.getEndTime());
        item.setLastOpen(newData.getLastOpen());
        item.setLastPeriod(newData.getLastPeriod());
        item.setName(newData.getName());
        item.setPeriod(newData.getPeriod());
        item.setPic(newData.getPic());
        // 页面不在滑动状态才更新
        if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE)
        {
            adapter.notifyItemChanged(position);
        }
        synchronized (refreshList)
        {
            refreshList.remove(item.getNum());
        }
    }

    class MyGouCaiViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_remain_index)
        TextView tvRemainIndex;
        @BindView(R.id.tv_remain_time)
        TextView tvRemainTime;
//        @BindView(R.id.tv_result)
//        TextView tvResult;
        @BindView(R.id.layout_result)
        LinearLayout layoutResult;
        ResultAssist resultAssist;

        public MyGouCaiViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.parent)
        public void onViewClicked(View view)
        {
            switch (view.getId())
            {
                case R.id.parent:
                    int position = getAdapterPosition();
                    if (position == -1)
                    {
                        return;
                    }
                    GouCaiBean.DataBean dataBean = mDataList.get(position);
                    mPresenter.requestRoomData(dataBean.getNum(), dataBean.getName());
                    break;
            }
        }
    }

    class MyGouCaiViewHolderGride extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public MyGouCaiViewHolderGride(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.parent)
        public void onViewClicked(View view)
        {
            switch (view.getId())
            {
                case R.id.parent:
                    int position = getAdapterPosition();
                    GouCaiBean.DataBean dataBean = mDataList.get(position);
                    mPresenter.requestRoomData(dataBean.getNum(), dataBean.getName());
                    break;
            }
        }
    }

    private class MyGouCaiAdapter extends RecyclerView.Adapter<MyGouCaiViewHolder>
    {

        @Override
        public MyGouCaiViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_linear_goucai, null);
            return new MyGouCaiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyGouCaiViewHolder holder1, int position)
        {
            GouCaiBean.DataBean dataBean = mDataList.get(position);
            holder1.tvTitle.setText(dataBean.getName());
            MyImageLoader.displayImage(HttpUtil.mNewUrl + dataBean.getPic(), holder1.iv, getContext());
            holder1.tvIndex.setText(getResources().getString(R.string.s_qishu, dataBean.getLastPeriod()));
            holder1.tvRemainIndex.setText(getResources().getString(R.string.s_qishu_jiezhi, dataBean
                    .getPeriod()));
            String lastOpen = dataBean.getLastOpen();
            // TODO 调整ResultAssist，BuyRoomBean对象这里没有，因为只是用到一个参数Num，所以就直接new一个代替
            BuyRoomBean bean = new BuyRoomBean();
            bean.setNum(dataBean.getNum());
            holder1.resultAssist = new ResultAssist(LayoutInflater.from(getContext()), holder1.layoutResult, bean, lastOpen, true);
//            if (StringUtils.isEmpty(lastOpen))
//            {
//                holder1.tvResult.setText("等待开奖");
//            }
//            else
//            {
//                holder1.tvResult.setText(lastOpen);
//            }
            String endTime = dataBean.getEndTime();
            if (!StringUtils.isEmpty(endTime))
            {
                long lEndTime = Long.valueOf(endTime);
                long currentTimeMillis = ServiceTime.getInstance().getCurrentTimeMillis();
                long temp = lEndTime - currentTimeMillis;
                if (temp <= 0)
                {
                    temp = 0;
                }
                long second = temp / 1000;
                if (second <= 0)
                {
                    Message msg = new Message();
                    msg.what = WHAT_REFRESH_ITEM;
                    msg.obj = dataBean.getNum();
                    mHandler.sendMessage(msg);
                    holder1.tvRemainTime.setText("");
                }
                else
                {
                    String timeStr = DateUtils.getHMS(second);
                    holder1.tvRemainTime.setText(timeStr);
                }

                // 独立分支用于计时5秒发送请求开奖结果
                if (second % LOOP_TIME == 0 && StringUtils.isEmpty(lastOpen))
                {
                    Message msg = new Message();
                    msg.what = WHAT_REFRESH_RESULT;
                    msg.obj = dataBean.getNum();
                    mHandler.sendMessage(msg);
                    //                    ToastUtil.show("发送请求开奖结果");
                }

            }

        }

        @Override
        public int getItemCount()
        {
            return null == mDataList ? 0 : mDataList.size();
        }
    }

    private class MyGouCaiGrideAdapter extends RecyclerView.Adapter<MyGouCaiViewHolderGride>
    {

        @Override
        public MyGouCaiViewHolderGride onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gride_goucai, null);
            return new MyGouCaiViewHolderGride(view);
        }

        @Override
        public void onBindViewHolder(MyGouCaiViewHolderGride holder1, int position)
        {
            GouCaiBean.DataBean dataBean = mDataList.get(position);
            holder1.tvTitle.setText(dataBean.getName());
            MyImageLoader.displayImage(HttpUtil.mNewUrl + dataBean.getPic(), holder1.iv, getContext());
        }

        @Override
        public int getItemCount()
        {
            return null == mDataList ? 0 : mDataList.size();
        }
    }
}
