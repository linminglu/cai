package com.example.admin.caipiao33.views.loadmore;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by fangxiao on 15/12/2.
 */
public class FooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private RecyclerView.Adapter adapter;

    private RecyclerView haoRecyclerView;

    private LoadingMoreFooter loadingMoreFooter;

    private static final int DEFAULT = 0;
    private static final int FOOTER = -1;

    public FooterAdapter(RecyclerView haoRecyclerView, LoadingMoreFooter loadingMoreFooter, RecyclerView.Adapter adapter)
    {
        this.haoRecyclerView = haoRecyclerView;
        this.adapter = adapter;
        this.loadingMoreFooter = loadingMoreFooter;
    }

    /**
     * 当前布局是否为Footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position)
    {
        return position < getItemCount() && position >= getItemCount() - 1;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == FOOTER)
        {
            return new SimpleViewHolder(loadingMoreFooter);
        }
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (adapter != null)
        {
            int count = adapter.getItemCount();
            if (position < count)
            {
                adapter.onBindViewHolder(holder, position);
                return;
            }
        }
    }

    @Override
    public int getItemCount()
    {
        if (adapter != null)
        {
            return 1 + adapter.getItemCount();
        }
        else
        {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isFooter(position))
        {
            return FOOTER;
        }
        if (adapter != null)
        {
            int count = adapter.getItemCount();
            if (position < count)
            {
                return adapter.getItemViewType(position);
            }
        }
        return DEFAULT;
    }

    @Override
    public long getItemId(int position)
    {
        if (adapter != null && position >= 0)
        {
            int adapterCount = adapter.getItemCount();
            if (position < adapterCount)
            {
                return adapter.getItemId(position);
            }
        }
        return -1;
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder
    {
        public SimpleViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}