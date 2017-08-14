package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.GridView4ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通可扩展listview的适配器
 */

public class NormalExpandableAdapter extends BaseAdapter
{
    private final BuyRoomBean mBuyRoomBean;
    private final LayoutInflater mInflater;
    private List<String> mCheckedList = new ArrayList<>();

    public NormalExpandableAdapter(LayoutInflater inflater, BuyRoomBean bean) {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
    }

    /**
     * 清空已选中的内容
     */
    public void clearChecked() {
        if (mCheckedList.size() > 0) {
            mCheckedList.clear();
            notifyDataSetChanged();
        }
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
            holder.gridView.setAdapter(new MyGridAdapter(playDetailListBean.getList()));
            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    BuyRoomBean.PlayDetailListBean playDetailListBean = (BuyRoomBean.PlayDetailListBean) parent.getTag(R.id.buy_holder);
                    BuyRoomBean.PlayDetailListBean.ListBean listBean = playDetailListBean.getList()
                            .get(position);
                    String playId = listBean.getPlayId();
                    if (mCheckedList.contains(playId)) {
                        mCheckedList.remove(playId);
                    } else {
                        mCheckedList.add(playId);
                    }
                    MyGridAdapter adapter = (MyGridAdapter) parent.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            });
            holder.tvName.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    BuyRoomBean.PlayDetailListBean playDetailListBean = (BuyRoomBean.PlayDetailListBean) v.getTag(R.id.buy_data);
                    playDetailListBean.setCollapsed(!playDetailListBean.isCollapsed());
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
            MyGridAdapter adapter = (MyGridAdapter) holder.gridView.getAdapter();
            adapter.setListData(playDetailListBean.getList());
        }
        holder.tvName.setText(name);
        holder.tvName.setTag(R.id.buy_data, playDetailListBean);
        holder.gridView.setTag(R.id.buy_holder, playDetailListBean);
        if (playDetailListBean.isCollapsed()) {
            holder.gridView.setVisibility(View.GONE);
        } else {
            holder.gridView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class GroupViewHolder {
        public TextView tvName;
        public GridView4ScrollView gridView;
    }

    private class MyGridAdapter extends BaseAdapter
    {

        private List<BuyRoomBean.PlayDetailListBean.ListBean> mList;

        public MyGridAdapter(List<BuyRoomBean.PlayDetailListBean.ListBean> list) {
            this.mList = list;
        }

        public void setListData(List<BuyRoomBean.PlayDetailListBean.ListBean> list) {
            this.mList = list;
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
            View layout = ViewHolder.get(convertView, R.id.layout);
            TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
            TextView tvOdds = ViewHolder.get(convertView, R.id.tv_odds);
            BuyRoomBean.PlayDetailListBean.ListBean listBean = mList.get(position);
            tvName.setText(listBean.getPlayName());
            tvOdds.setText(listBean.getBonus());
            String playId = listBean.getPlayId();
            if (mCheckedList.contains(playId)) {
                layout.setBackgroundResource(R.drawable.liuhecai_btn_xuanzhong_02);
            } else {
                layout.setBackgroundResource(R.drawable.liuhecai_btn_weixuan_01);
            }
            return convertView;
        }
    }
}
