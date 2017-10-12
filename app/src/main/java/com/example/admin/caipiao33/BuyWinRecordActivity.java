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
import android.widget.TextView;

import com.example.admin.caipiao33.bean.BuyRecordBean;
import com.example.admin.caipiao33.contract.IBuyRecordContract;
import com.example.admin.caipiao33.presenter.BuyRecordPresenter;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.Tools;
import com.example.admin.caipiao33.views.DividerItemDecoration;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.loadmore.LoadMoreHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 中奖记录
 */
public class BuyWinRecordActivity extends BaseActivity implements IBuyRecordContract.View
{
    private final static String TYPE_WIN = "1"; // 中奖
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private IBuyRecordContract.Presenter mPresenter;
    private BuyRecordBean mBuyRecordBean;
    private MyAdapter adapter;
    private LoadMoreHelper helper;
    private String mType = TYPE_WIN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_win_record);
        ButterKnife.bind(this);
        initView();

        // 修改toolbar内容
        mToolbar.setTitle("");
        mToolbarTitle.setText("中奖记录");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new BuyRecordPresenter(this);
        mPresenter.loadData(mType, "1");

        // 加载
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        helper = new LoadMoreHelper(this);
        helper.setLoadMoreListener(new LoadMoreHelper.LoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                mPresenter.loadMore(mType, String.valueOf(mBuyRecordBean.getPageNo() + 1));
            }
        });
        helper.setBindingRecyclerView(recyclerView, adapter);
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.loadData(mType, "1");
            }
        });
        Tools.setSwipeRefreshColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                helper.refreshComplete();
                mPresenter.refreshData(mType, "1");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateHomePage(BuyRecordBean bean)
    {
        this.mBuyRecordBean = bean;
        adapter.notifyDataSetChanged();
        int pageNo = mBuyRecordBean.getPageNo();
        int totalPage = mBuyRecordBean.getTotalPage();
        if (pageNo == totalPage)
        {
            helper.loadMoreEnd();
        }
    }

    @Override
    public void updateMoreData(BuyRecordBean bean)
    {
        if (null == bean)
        {
            return;
        }
        int pageNo = bean.getPageNo();
        int totalPage = bean.getTotalPage();
        mBuyRecordBean.setPageNo(pageNo);
        mBuyRecordBean.setTotalPage(totalPage);
        if (pageNo == totalPage)
        {
            helper.loadMoreEnd();
        }
        else
        {
            helper.loadMoreComplete();
        }
        List<BuyRecordBean.ItemsBean> items = bean.getItems();
        if (null == items)
        {
            return;
        }
        List<BuyRecordBean.ItemsBean> items1 = mBuyRecordBean.getItems();
        items1.addAll(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void hideRefreshing()
    {
        swipeRefreshLayout.setRefreshing(false);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_index)
        TextView tvIndex;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_win)
        TextView tvWin;

        public MyViewHolder(View itemView)
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
                    BuyRecordBean.ItemsBean itemsBean = mBuyRecordBean.getItems().get(position);
                    Intent intent = new Intent(BuyWinRecordActivity.this, BuyDetailActivity.class);
                    intent.putExtra(Constants.EXTRA_BUY_RECORD_ID, itemsBean.getId());
                    intent.putExtra(Constants.EXTRA_BUY_GAME_ID, itemsBean.getgId());
                    startActivity(intent);
                    break;
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = getLayoutInflater().inflate(R.layout.item_buy_record, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            BuyRecordBean.ItemsBean itemsBean = mBuyRecordBean.getItems().get(position);
            holder.tvName.setText(itemsBean.getGameName());
            holder.tvIndex.setText(getString(R.string.s_qishu, itemsBean.getPeriod()));
            holder.tvMoney.setText(getString(R.string.s_money, "-" + itemsBean.getAmount()));
            holder.tvTime.setText(itemsBean.getAddTime());
            if (itemsBean.getIsWin() == 1)
            { // isWin=1中奖 -1未中奖
                holder.tvWin.setTextColor(getResources().getColor(R.color.c_homepage_4));
                holder.tvWin.setText(getString(R.string.s_win_money, itemsBean.getWinAmount()));
            }
            else
            {
                holder.tvWin.setTextColor(getResources().getColor(R.color.middle_gray));
                holder.tvWin.setText(getString(R.string.s_not_win));
            }
        }

        @Override
        public int getItemCount()
        {
            if (null == mBuyRecordBean)
            {
                return 0;
            }
            List<BuyRecordBean.ItemsBean> items = mBuyRecordBean.getItems();
            return items == null ? 0 : items.size();
        }
    }

    @Override
    public void showErrorMsg(String msg)
    {
        ToastUtil.show(msg);
    }
}
