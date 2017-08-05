package com.example.admin.caipiao33.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.LazyFragment;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.utils.Tools;
import com.example.admin.caipiao33.views.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 购彩子项目
 * 全部彩种、高频彩、低频彩
 */
public class GouCaiItemFragment extends LazyFragment
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        adapter = new MyGouCaiAdapter();
        grideAdapter = new MyGouCaiGrideAdapter();
        recyclerView.setAdapter(adapter);
        isCreate = true;
        lazyLoad();
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

    public void refreshRecyclerView() {
        if (null == mGouCaiBean) {
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
        if (fragment.isLinearLayout) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && adapter instanceof MyGouCaiAdapter) {
                adapter.notifyDataSetChanged();
            } else {
                updateUILayout();
            }
        } else {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && adapter instanceof MyGouCaiGrideAdapter) {
                adapter.notifyDataSetChanged();
            } else {
                updateUILayout();
            }
        }
    }

    public void updateUILayout() {
        GouCaiFragment fragment = (GouCaiFragment) getParentFragment();
        if (fragment.isLinearLayout) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
            recyclerView.setAdapter(adapter);
        } else {
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
        @BindView(R.id.tv_result)
        TextView tvResult;

        public MyGouCaiViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
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
            holder1.tvIndex.setText(getResources().getString(R.string.s_qishu, dataBean.getPeriod()));
            holder1.tvRemainIndex.setText(getResources().getString(R.string.s_qishu_jiezhi, dataBean.getLastPeriod()));
            holder1.tvResult.setText(dataBean.getLastOpen());
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
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_gride_goucai, null);
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
