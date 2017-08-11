package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.GridView4ScrollView;

import java.util.List;

/**
 * 普通可扩展listview的适配器
 */

public class NormalExpandableAdapter extends BaseAdapter
{
    private final BuyRoomBean mBuyRoomBean;
    private final LayoutInflater mInflater;

    public NormalExpandableAdapter(LayoutInflater inflater, BuyRoomBean bean) {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
    }

    @Override
    public int getCount()
    {
        return mBuyRoomBean.getPlayDetailList().size();
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
        BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                .get(position);
        String name = playDetailListBean.getName();
        GroupViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group, null);
            holder = new GroupViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.gridView = (GridView4ScrollView) convertView.findViewById(R.id.gridView4ScrollView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        holder.tvName.setText(name);
        holder.gridView.setAdapter(new MyGridAdapter(playDetailListBean.getList()));
        return convertView;
    }

    private class GroupViewHolder {
        public TextView tvName;
        public GridView4ScrollView gridView;
    }

    private class MyGridAdapter extends BaseAdapter {

        private List<BuyRoomBean.PlayDetailListBean.ListBean> mList;

        public MyGridAdapter(List<BuyRoomBean.PlayDetailListBean.ListBean> list) {
            this.mList = list;
        }

        public void setListData(List<BuyRoomBean.PlayDetailListBean.ListBean> list) {
            this.mList.clear();
            this.mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public int getCount()
        {
            return mList.size();
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
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.item_buy_quick, null);
            }
            TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
            TextView tvOdds = ViewHolder.get(convertView, R.id.tv_odds);
            BuyRoomBean.PlayDetailListBean.ListBean listBean = mList.get(position);
            tvName.setText(listBean.getPlayName());
            tvOdds.setText(listBean.getBonus());
            return convertView;
        }
    }
}
