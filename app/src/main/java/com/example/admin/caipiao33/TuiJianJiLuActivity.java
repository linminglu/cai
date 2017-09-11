package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.TuiJianJiLuBean;
import com.example.admin.caipiao33.contract.ITuiJianJiLuContract;
import com.example.admin.caipiao33.presenter.TuiJianJiLuPresenter;
import com.example.admin.caipiao33.views.DividerItemDecoration;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.loadmore.LoadMoreHelper;

import java.util.ArrayList;
import java.util.List;

//推荐记录
public class TuiJianJiLuActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ITuiJianJiLuContract.View
{

    private SwipeRefreshLayout mNotifySwipe;
    private RecyclerView mNotifyRecycler;
    private View mNotifyNullLayout;
    private ArrayList<TuiJianJiLuBean.ItemsBean> mList = new ArrayList<>();
    private ITuiJianJiLuContract.Presenter mPresenter;
    private MyAdapter mAdapter;
    private LoadMoreHelper helper;
    private int currPage = 1;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiandaojilu);
        initView();
        mPresenter = new TuiJianJiLuPresenter(this, mNotifySwipe);
        mPresenter.getTuiJianJiLu();
    }


    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_tuijian_jilu);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    private void initView()
    {
        mNotifySwipe = (SwipeRefreshLayout) findViewById(R.id.notify_swipe);
        mNotifySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                currPage = 1;
                mPresenter.getTuiJianJiLu();
            }
        });
        mNotifyRecycler = (RecyclerView) findViewById(R.id.notify_recycler);
        mNotifyRecycler.setLayoutManager(new LinearLayoutManager(this));
        mNotifyRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new MyAdapter();
        mNotifyRecycler.setAdapter(mAdapter);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                currPage = 1;
                mPresenter.getTuiJianJiLu();
            }
        });
        mNotifyNullLayout = findViewById(R.id.notify_null_layout);
        helper = new LoadMoreHelper(this);
        helper.setLoadMoreListener(new LoadMoreHelper.LoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                mPresenter.getMoreJiLu(currPage++);
            }
        });
        helper.setBindingRecyclerView(mNotifyRecycler, mAdapter);
    }

    @Override
    public void updata(TuiJianJiLuBean result)
    {
        mNotifySwipe.setRefreshing(false);
        mList = result.getItems();
        currPage = result.getPageNo();
        total = result.getTotalPage();
        // 合并数据
        if (null == mList || mList.size() == 0)
        {
            mNotifySwipe.setVisibility(View.GONE);
            mNotifyNullLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mNotifySwipe.setVisibility(View.VISIBLE);
            mNotifyNullLayout.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
        // 设置到底了
        if (currPage == total)
        {
            helper.loadMoreEnd();
        }
        else
        {
            helper.loadMoreComplete();
        }
    }

    @Override
    public void loadmore(TuiJianJiLuBean result)
    {
        mNotifySwipe.setRefreshing(false);
        List<TuiJianJiLuBean.ItemsBean> content = result.getItems();
        currPage = result.getPageNo();
        total = result.getTotalPage();
        // 合并数据
        mList.addAll(content);
        if (null == mList || mList.size() == 0)
        {
            mNotifySwipe.setVisibility(View.GONE);
            mNotifyNullLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mNotifySwipe.setVisibility(View.VISIBLE);
            mNotifyNullLayout.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
        // 设置到底了
        if (currPage == total)
        {
            helper.loadMoreEnd();
        }
        else
        {
            helper.loadMoreComplete();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView item_tuijianjilu_checkinDay;
        public TextView item_tuijianjilu_recharge;
        public TextView item_tuijianjilu_giftAmount;
        public TextView item_tuijianjilu_checkinTime;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_tuijianjilu_checkinDay = (TextView) itemView.findViewById(R.id.item_tuijianjilu_checkinDay);
            item_tuijianjilu_recharge = (TextView) itemView.findViewById(R.id.item_tuijianjilu_recharge);
            item_tuijianjilu_giftAmount = (TextView) itemView.findViewById(R.id.item_tuijianjilu_giftAmount);
            item_tuijianjilu_checkinTime = (TextView) itemView.findViewById(R.id.item_tuijianjilu_checkinTime);
        }

        @Override
        public void onClick(View v)
        {

        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        public MyAdapter()
        {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = getLayoutInflater().inflate(R.layout.item_tuijianjilu, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            TuiJianJiLuBean.ItemsBean itemsBean = mList.get(position);
            holder.item_tuijianjilu_checkinDay.setText(itemsBean.getYearmonth());
            holder.item_tuijianjilu_recharge.setText(itemsBean.getAddTime());
            holder.item_tuijianjilu_giftAmount.setText(itemsBean.getAmount() + "元");
            holder.item_tuijianjilu_checkinTime.setText("投注总额：" + itemsBean.getTotalBet() + "元");
        }

        @Override
        public int getItemCount()
        {
            return null == mList ? 0 : mList.size();
        }

    }
}

