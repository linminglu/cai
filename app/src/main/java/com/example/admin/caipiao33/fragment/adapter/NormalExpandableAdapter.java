package com.example.admin.caipiao33.fragment.adapter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.GridView4ScrollView;
import com.example.admin.caipiao33.views.NumberInputFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 普通可扩展listview的适配器
 */

public class NormalExpandableAdapter extends BaseAdapter
{
    private final BuyRoomBean mBuyRoomBean;
    private final LayoutInflater mInflater;
    private final int mType;
    private List<BuyRoomBean.PlayDetailListBean.ListBean> mCheckedList = new ArrayList<>();
    private HashMap<BuyRoomBean.PlayDetailListBean.ListBean, String> mNumberMap = new HashMap<>();

    public NormalExpandableAdapter(LayoutInflater inflater, BuyRoomBean bean, int type) {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
        this.mType = type;
    }

    /**
     * 清空已选中的内容
     */
    public void clearChecked() {
        if (mCheckedList.size() > 0) {
            mCheckedList.clear();
            notifyDataSetChanged();
        }
        if (mNumberMap.size() > 0) {
            mNumberMap.clear();
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
                    if (mCheckedList.contains(listBean)) {
                        mCheckedList.remove(listBean);
                    } else {
                        mCheckedList.add(listBean);
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
        public int getViewTypeCount()
        {
            return 2;
        }

        @Override
        public int getItemViewType(int position)
        {
            return mType;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            // 快捷下注的类型
            if (getItemViewType(position) == QuickBuyFragment.TYPE_QUICK) {
                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.item_buy_quick, null);
                }
                View layout = ViewHolder.get(convertView, R.id.layout);
                TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
                TextView tvOdds = ViewHolder.get(convertView, R.id.tv_odds);
                BuyRoomBean.PlayDetailListBean.ListBean listBean = mList.get(position);
                tvName.setText(listBean.getPlayName());
                tvOdds.setText(listBean.getBonus());
                if (mCheckedList.contains(listBean)) {
                    layout.setBackgroundResource(R.drawable.liuhecai_btn_xuanzhong_02);
                } else {
                    layout.setBackgroundResource(R.drawable.liuhecai_btn_weixuan_01);
                }
                return convertView;
            } else {
                // 自选下注的类型
                if (null == convertView) {
                    convertView = mInflater.inflate(R.layout.item_buy_self_select, null);
                    final EditText etNum = ViewHolder.get(convertView, R.id.et_num);
                    etNum.setFilters(new InputFilter[]{new NumberInputFilter()});
                    etNum.addTextChangedListener(new TextWatcher()
                    {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after)
                        {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count)
                        {

                        }

                        @Override
                        public void afterTextChanged(Editable s)
                        {
                            String s1 = s.toString();
                            BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) etNum.getTag(R.id.buy_data);
                            if (TextUtils.isEmpty(s1)) {
                                // 如果最新值为空，直接移除该项目
                                if (mNumberMap.containsKey(listBean)) {
                                    mNumberMap.remove(listBean);
                                }
                                // 原来没有保存值，最新的值也是为空的话就直接忽略
                                return;
                            }
                            mNumberMap.put(listBean, s1);
                        }
                    });
                }
                TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
                EditText etNum = ViewHolder.get(convertView, R.id.et_num);
                BuyRoomBean.PlayDetailListBean.ListBean listBean = mList.get(position);
                tvName.setText(listBean.getPlayName() + listBean.getBonus());

                etNum.setTag(R.id.buy_data, listBean);
                if (mNumberMap.containsKey(listBean)) {
                    etNum.setText(mNumberMap.get(listBean));
                } else {
                    etNum.setText("");
                }
                return convertView;
            }
        }
    }
}
