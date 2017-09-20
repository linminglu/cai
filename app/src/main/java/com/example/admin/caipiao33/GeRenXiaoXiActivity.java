package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.GeRenXiaoXiBean;
import com.example.admin.caipiao33.contract.IGeRenXiaoXiContract;
import com.example.admin.caipiao33.presenter.GeRenXiaoXiPresenter;
import com.example.admin.caipiao33.views.DividerItemDecoration;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.loadmore.LoadMoreHelper;

import java.util.ArrayList;
import java.util.List;


public class GeRenXiaoXiActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IGeRenXiaoXiContract.View
{

    private SwipeRefreshLayout mNotifySwipe;
    private RecyclerView mNotifyRecycler;
    private View mNotifyNullLayout;
    private ArrayList<GeRenXiaoXiBean.ItemsBean> mList = new ArrayList<>();
    private IGeRenXiaoXiContract.Presenter mPresenter;
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
        mPresenter = new GeRenXiaoXiPresenter(this, mNotifySwipe);
    }


    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle("个人消息列表");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mNotifySwipe.setRefreshing(true);
        mPresenter.getGeRenXiaoXi();
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
                mPresenter.getGeRenXiaoXi();
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
                mPresenter.getGeRenXiaoXi();
            }
        });
        mNotifyNullLayout = findViewById(R.id.notify_null_layout);
        helper = new LoadMoreHelper(this);
        helper.setLoadMoreListener(new LoadMoreHelper.LoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                mPresenter.getMoreXiaoXi(currPage++);
            }
        });
        helper.setBindingRecyclerView(mNotifyRecycler, mAdapter);
    }

    @Override
    public void updata(GeRenXiaoXiBean result)
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
    public void loadmore(GeRenXiaoXiBean result)
    {
        mNotifySwipe.setRefreshing(false);
        List<GeRenXiaoXiBean.ItemsBean> content = result.getItems();
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
        public TextView item_gerenxiaoxi_title;
        public TextView item_gerenxiaoxi_time;
        public TextView item_gerenxiaoxi_yidu;
        public LinearLayout notify_parent;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            item_gerenxiaoxi_title = (TextView) itemView.findViewById(R.id.item_gerenxiaoxi_title);
            item_gerenxiaoxi_time = (TextView) itemView.findViewById(R.id.item_gerenxiaoxi_time);
            item_gerenxiaoxi_yidu = (TextView) itemView.findViewById(R.id.item_gerenxiaoxi_yidu);
            notify_parent = (LinearLayout) itemView.findViewById(R.id.notify_parent);
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
            View view = getLayoutInflater().inflate(R.layout.item_gerenxiaoxi, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            GeRenXiaoXiBean.ItemsBean itemsBean = mList.get(position);
            holder.item_gerenxiaoxi_yidu.setVisibility(itemsBean.getStatus() == 1 ? View.GONE : View.VISIBLE);
            holder.item_gerenxiaoxi_title.setText(itemsBean.getTitle());
            holder.item_gerenxiaoxi_time.setText(itemsBean.getAddTime());
            holder.notify_parent.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(GeRenXiaoXiActivity.this, GeRenMsgActivity.class);
                    intent.putExtra("id", mList.get(position).getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return null == mList ? 0 : mList.size();
        }

    }
}

