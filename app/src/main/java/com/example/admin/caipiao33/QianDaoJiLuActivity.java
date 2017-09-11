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

import com.example.admin.caipiao33.bean.QianDaoJiLuBean;
import com.example.admin.caipiao33.contract.IQianDaoJiLuContract;
import com.example.admin.caipiao33.presenter.QianDaoJiLuPresenter;
import com.example.admin.caipiao33.views.DividerItemDecoration;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.loadmore.LoadMoreHelper;

import java.util.ArrayList;
import java.util.List;


public class QianDaoJiLuActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IQianDaoJiLuContract.View
{

    private SwipeRefreshLayout mNotifySwipe;
    private RecyclerView mNotifyRecycler;
    private View mNotifyNullLayout;
    private ArrayList<QianDaoJiLuBean.ItemsBean> mList = new ArrayList<>();
    private IQianDaoJiLuContract.Presenter mPresenter;
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
        mPresenter = new QianDaoJiLuPresenter(this, mNotifySwipe);
        mPresenter.getQianDaoJiLu();
    }


    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_qiandao_jilu);
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
                mPresenter.getQianDaoJiLu();
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
                mPresenter.getQianDaoJiLu();
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
    public void updata(QianDaoJiLuBean result)
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
    public void loadmore(QianDaoJiLuBean result)
    {
        mNotifySwipe.setRefreshing(false);
        List<QianDaoJiLuBean.ItemsBean> content = result.getItems();
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
        public TextView item_qiandaojilu_checkinDay;
        public TextView item_qiandaojilu_recharge;
        public TextView item_qiandaojilu_giftAmount;
        public TextView item_qiandaojilu_checkinTime;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_qiandaojilu_checkinDay = (TextView) itemView.findViewById(R.id.item_qiandaojilu_checkinDay);
            item_qiandaojilu_recharge = (TextView) itemView.findViewById(R.id.item_qiandaojilu_recharge);
            item_qiandaojilu_giftAmount = (TextView) itemView.findViewById(R.id.item_qiandaojilu_giftAmount);
            item_qiandaojilu_checkinTime = (TextView) itemView.findViewById(R.id.item_qiandaojilu_checkinTime);
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
            View view = getLayoutInflater().inflate(R.layout.item_qiandaojilu, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            QianDaoJiLuBean.ItemsBean itemsBean = mList.get(position);
            holder.item_qiandaojilu_checkinDay.setText(itemsBean.getCheckinDay());
            holder.item_qiandaojilu_checkinTime.setText(itemsBean.getCheckinTime());
            holder.item_qiandaojilu_giftAmount.setText(itemsBean.getGiftAmount() + "元");
            holder.item_qiandaojilu_recharge.setText("充值总额：" + itemsBean.getRecharge() + "元");
        }

        @Override
        public int getItemCount()
        {
            return null == mList ? 0 : mList.size();
        }

    }
}

