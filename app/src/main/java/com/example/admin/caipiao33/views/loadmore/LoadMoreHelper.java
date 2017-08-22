package com.example.admin.caipiao33.views.loadmore;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by lsd on 2016/5/24.
 */
public class LoadMoreHelper
{
    // 加载更多监听
    private LoadMoreListener mListener;
    // 加载更多的FooterView是否显示
    private boolean isShowFooterView = false;
    // 是否到底了，到底了不需要再加载更多的逻辑
    private boolean isEnd = false;
    //
    private LoadMoreScrollListener mLoadMoreScrollListener;

    private View mFooterView;
    private LoadingMoreFooter footView;
    private ViewGroup mViewGroup;
    //
    private boolean isLoadingData = false;
    private FooterAdapter mFooterAdapter;
    private Context mContext;


    public LoadMoreHelper(Context context)
    {
        this.mContext = context;
    }

    // 设置加载更多监听
    public void setLoadMoreListener(LoadMoreListener listener)
    {
        this.mListener = listener;
    }

    // 设置滑动监听
    public void setLoadMoreScrollListener(LoadMoreScrollListener listener)
    {
        this.mLoadMoreScrollListener = listener;
    }

    public void setBindingListView(ListView lv)
    {
        this.mViewGroup = lv;
        footView = new LoadingMoreFooter(mContext);
        lv.addFooterView(footView);
        footView.setGone();
        lv.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            //当前ListView中最后一个Item的索引
            int lastItemIndex = 0;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                //当ListView不在滚动，并且ListView的最后一项的索引等于adapter的项数减一时则自动加载（因为索引是从0开始的）
                int itemCount = getItemCount();
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && !isEnd && !isLoadingData && itemCount > 1 && lastItemIndex == itemCount - 1 && mListener != null)
                {
                    if (footView != null)
                    {
                        footView.setVisible();
                    }
                    isLoadingData = true;
                    mListener.onLoadMore();
                }


                if (null != mLoadMoreScrollListener)
                {
                    mLoadMoreScrollListener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                //最后一个item的位置
                lastItemIndex = firstVisibleItem + visibleItemCount - 1;
                if (null != mLoadMoreScrollListener)
                {
                    mLoadMoreScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    public void setBindingRecyclerView(RecyclerView rv, RecyclerView.Adapter adapter)
    {
        this.mViewGroup = rv;
        rv.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isEnd && mListener != null && !isLoadingData)
                {

                    RecyclerView.LayoutManager layoutManager = ((RecyclerView) mViewGroup).getLayoutManager();
                    int lastVisibleItemPosition;
                    if (layoutManager instanceof GridLayoutManager)
                    {
                        lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }
                    else if (layoutManager instanceof StaggeredGridLayoutManager)
                    {
                        int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                        lastVisibleItemPosition = last(into);
                    }
                    else
                    {
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    int itemCount = getItemCount();

                    if (itemCount > 1 && lastVisibleItemPosition >= layoutManager.getItemCount() - 1)
                    {
                        if (footView != null)
                        {
                            footView.setVisible();
                        }
                        isLoadingData = true;
                        mListener.onLoadMore();
                    }
                }

                if (null != mLoadMoreScrollListener)
                {
                    mLoadMoreScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);

                if (null != mLoadMoreScrollListener)
                {
                    mLoadMoreScrollListener.onScrolled(recyclerView, dx, dy);
                }
            }
        });

        footView = new LoadingMoreFooter(mContext);
        footView.setGone();
        mFooterAdapter = new FooterAdapter(rv, footView, adapter);
        rv.setAdapter(mFooterAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
    }

    //取到最后的一个节点
    private int last(int[] lastPositions)
    {
        int last = lastPositions[0];
        for (int value : lastPositions)
        {
            if (value > last)
            {
                last = value;
            }
        }
        return last;
    }

    private int getItemCount()
    {
        if (mViewGroup instanceof ListView)
        {
            return ((ListView) mViewGroup).getAdapter().getCount();
        }
        else if (mViewGroup instanceof RecyclerView)
        {
            return ((RecyclerView) mViewGroup).getAdapter().getItemCount();
        }
        return -1;
    }

    //    //设置底部加载中效果
    //    public void setFootLoadingView(View view) {
    //        if (footView != null) {
    //            footView.addFootLoadingView(view);
    //        }
    //    }

    //设置底部到底了布局
    public void setFootEndView(View view)
    {
        if (footView != null)
        {
            footView.addFootEndView(view);
        }
    }

    //下拉刷新后初始化底部状态
    public void refreshComplete()
    {
        if (footView != null)
        {
            footView.setGone();
        }
        isLoadingData = false;
        isEnd = false;
    }

    public void loadMoreComplete()
    {
        if (footView != null)
        {
            footView.setGone();
        }
        isLoadingData = false;
    }


    //到底了
    // 必须在adater更新数据之后调用
    public void loadMoreEnd()
    {
        if (footView != null)
        {
            footView.setEnd();
        }
        isEnd = true;
        if (getItemCount() < 5)
        {
            footView.setGone();
        }
    }

    // 加载更多的监听
    public interface LoadMoreListener
    {
        // 回调加载更多逻辑
        void onLoadMore();
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver()
    {
        @Override
        public void onChanged()
        {
            mFooterAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount)
        {
            mFooterAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount)
        {
            mFooterAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload)
        {
            mFooterAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount)
        {
            mFooterAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount)
        {
            mFooterAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };
}
