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

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.bean.ZhangHuMingXiBean;
import com.example.admin.caipiao33.contract.IZhangHuMingXiContract;
import com.example.admin.caipiao33.presenter.ZhangHuMingXiPresenter;
import com.example.admin.caipiao33.views.DividerItemDecoration;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.example.admin.caipiao33.views.loadmore.LoadMoreHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//账户明细
public class ZhangHuMingXiActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, IZhangHuMingXiContract.View
{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private SwipeRefreshLayout mNotifySwipe;
    private RecyclerView mNotifyRecycler;
    private View mNotifyNullLayout;
    private ArrayList<ZhangHuMingXiBean.ItemsBean> mList = new ArrayList<>();
    private IZhangHuMingXiContract.Presenter mPresenter;
    private MyAdapter mAdapter;
    private LoadMoreHelper helper;
    private int currPage = 1;
    private String type = "0";
    private int total;
    private String[] typenames;
    private String[] typenums;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhanghumingxi);
        ButterKnife.bind(this);
        initView();
        typenames = getResources().getStringArray(R.array.s_array_mingxi_type);
        typenums = getResources().getStringArray(R.array.s_array_mingxi_type_num);
        mPresenter = new ZhangHuMingXiPresenter(this, mNotifySwipe);
        mPresenter.getZhangHuMingXi(type);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    private void initView()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mNotifySwipe = (SwipeRefreshLayout) findViewById(R.id.notify_swipe);
        mNotifySwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                currPage = 1;
                mPresenter.getZhangHuMingXi(type);
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
                mPresenter.getZhangHuMingXi(type);
            }
        });
        mNotifyNullLayout = findViewById(R.id.notify_null_layout);
        helper = new LoadMoreHelper(this);
        helper.setLoadMoreListener(new LoadMoreHelper.LoadMoreListener()
        {
            @Override
            public void onLoadMore()
            {
                mPresenter.getMoreMingXi(type, currPage++);
            }
        });
        helper.setBindingRecyclerView(mNotifyRecycler, mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updata(ZhangHuMingXiBean result)
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
    public void loadmore(ZhangHuMingXiBean result)
    {
        mNotifySwipe.setRefreshing(false);
        List<ZhangHuMingXiBean.ItemsBean> content = result.getItems();
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

    @OnClick(R.id.toolbar_title)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.toolbar_title:
                showOptionsDialog();
                break;
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
            ZhangHuMingXiBean.ItemsBean itemsBean = mList.get(position);
            holder.item_tuijianjilu_checkinDay.setText(itemsBean.getExpand().getRemark());
            holder.item_tuijianjilu_recharge.setText("单号" + itemsBean.getOrderNo());
            if (itemsBean.getInOut() == 1)
            {
                holder.item_tuijianjilu_giftAmount.setTextColor(getResources().getColor(R.color.green));
                holder.item_tuijianjilu_giftAmount.setText("+" + itemsBean.getAmount() + "元");
            }
            else
            {
                holder.item_tuijianjilu_giftAmount.setTextColor(getResources().getColor(R.color.red));
                holder.item_tuijianjilu_giftAmount.setText("-" + itemsBean.getAmount() + "元");
            }
            holder.item_tuijianjilu_checkinTime.setText("余额：" + itemsBean.getAmountAfter() + "元");
        }

        @Override
        public int getItemCount()
        {
            return null == mList ? 0 : mList.size();
        }

    }

    private void showOptionsDialog()
    {
        if (null == typenames || null == typenums)
        {
            return;
        }
        new MaterialDialog.Builder(ZhangHuMingXiActivity.this).title("玩法选择")
                .items(typenames)
                .positiveText(R.string.dialog_ok)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice()
                {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
                    {
                        if (which != -1)
                        {
                            type = typenums[which];
                            toolbarTitle.setText(typenames[which]);
                            currPage = 1;
                            mPresenter.getZhangHuMingXi(type);
                        }
                        return true;
                    }
                })
                .show();
    }
}

