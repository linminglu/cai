package com.example.admin.caipiao33.fragment.adapter;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.GridView4ScrollView;
import com.example.admin.caipiao33.views.NumberInputFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 前四个四个折叠的适配器
 */

public class TypeBeforeAdapter extends MyBaseBuyAdapter
{
    private static final int COUNT = 4;
    private final BuyRoomBean mBuyRoomBean;
    private final LayoutInflater mInflater;
    private final int mType;
    private List<BuyRoomBean.PlayDetailListBean.ListBean> mCheckedList = new ArrayList<>();
    private HashMap<BuyRoomBean.PlayDetailListBean.ListBean, String> mNumberMap = new HashMap<>();

    public TypeBeforeAdapter(LayoutInflater inflater, BuyRoomBean bean, int type) {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
        this.mType = type;
    }

    /**
     * 清空已选中的内容
     */
    @Override
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
        int size = mBuyRoomBean.getPlayDetailList().size();
        return size / COUNT + size % COUNT;
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
        int size = mBuyRoomBean.getPlayDetailList().size();
        BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                .get(position);
        GroupViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group_before, null);
            holder = new GroupViewHolder();
            holder.layout = convertView.findViewById(R.id.layout);
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv_1);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv_2);
            holder.tv3 = (TextView) convertView.findViewById(R.id.tv_3);
            holder.tv4 = (TextView) convertView.findViewById(R.id.tv_4);
            holder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            holder.gridView = (GridView4ScrollView) convertView.findViewById(R.id.gridView4ScrollView);

            if (position < size / COUNT) {
                int maxSingleItemSize = 0;
                for (int i = 0; i < COUNT; i++) {
                    BuyRoomBean.PlayDetailListBean playDetailListBean1 = mBuyRoomBean.getPlayDetailList().get(COUNT * position + i);
                    if (maxSingleItemSize < playDetailListBean1.getList().size()) {
                        maxSingleItemSize = playDetailListBean1.getList().size();
                    }
                    switch (i) {
                        case 0:
                            holder.tv1.setText(playDetailListBean1.getName());
                            break;
                        case 1:
                            holder.tv2.setText(playDetailListBean1.getName());
                            break;
                        case 2:
                            holder.tv3.setText(playDetailListBean1.getName());
                            break;
                        case 3:
                            holder.tv4.setText(playDetailListBean1.getName());
                            break;
                        default:
                            break;
                    }
                }
                int itemSize = maxSingleItemSize * COUNT;
                List<BuyRoomBean.PlayDetailListBean.ListBean> list = new ArrayList<>(itemSize);
                for (int i = 0; i < itemSize; i++) {
                    list.add(null);
                }
                for (int i = 0; i < COUNT; i++) {
                    List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = mBuyRoomBean.getPlayDetailList()
                            .get(COUNT * position + i)
                            .getList();
                    for (int j = 0; j < itemList.size(); j++) {
                        list.set(COUNT * j + i, itemList.get(j));
                    }
                }
                holder.gridView.setAdapter(new MyGridAdapter(list));
                holder.gridView.setTag(R.id.buy_holder, list);
            } else {
                holder.tv1.setText(playDetailListBean.getName());
                holder.tv2.setText("");
                holder.tv3.setText("");
                holder.tv4.setText("");
                holder.gridView.setAdapter(new MyGridAdapter(playDetailListBean.getList()));
                holder.gridView.setTag(R.id.buy_holder, playDetailListBean.getList());
            }

            holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    List<BuyRoomBean.PlayDetailListBean.ListBean> list = (List<BuyRoomBean.PlayDetailListBean.ListBean>) parent.getTag(R.id.buy_holder);
                    BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(position);
                    if (null == listBean) {
                        return;
                    }
                    if (mCheckedList.contains(listBean)) {
                        mCheckedList.remove(listBean);
                    } else {
                        mCheckedList.add(listBean);
                    }
                    MyGridAdapter adapter = (MyGridAdapter) parent.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            });

            holder.layout.setOnClickListener(new View.OnClickListener()
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
            if (position < size / COUNT) {
                int maxSingleItemSize = 0;
                for (int i = 0; i < COUNT; i++) {
                    BuyRoomBean.PlayDetailListBean playDetailListBean1 = mBuyRoomBean.getPlayDetailList().get(COUNT * position + i);
                    if (maxSingleItemSize < playDetailListBean1.getList().size()) {
                        maxSingleItemSize = playDetailListBean1.getList().size();
                    }
                    switch (i) {
                        case 0:
                            holder.tv1.setText(playDetailListBean1.getName());
                            break;
                        case 1:
                            holder.tv2.setText(playDetailListBean1.getName());
                            break;
                        case 2:
                            holder.tv3.setText(playDetailListBean1.getName());
                            break;
                        case 3:
                            holder.tv4.setText(playDetailListBean1.getName());
                            break;
                        default:
                            break;
                    }
                }
                int itemSize = maxSingleItemSize * COUNT;
                List<BuyRoomBean.PlayDetailListBean.ListBean> list = new ArrayList<>(itemSize);
                for (int i = 0; i < itemSize; i++) {
                    list.add(null);
                }
                for (int i = 0; i < COUNT; i++) {
                    List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = mBuyRoomBean.getPlayDetailList()
                            .get(COUNT * position + i)
                            .getList();
                    for (int j = 0; j < itemList.size(); j++) {
                        list.set(COUNT * j + i, itemList.get(j));
                    }
                }
                adapter.setListData(list);
                holder.gridView.setTag(R.id.buy_holder, list);
            } else {
                holder.tv1.setText(playDetailListBean.getName());
                holder.tv2.setText("");
                holder.tv3.setText("");
                holder.tv4.setText("");
                adapter.setListData(playDetailListBean.getList());
                holder.gridView.setTag(R.id.buy_holder, playDetailListBean.getList());
            }
        }
        holder.layout.setTag(R.id.buy_data, playDetailListBean);
        if (playDetailListBean.isCollapsed()) {
            holder.gridView.setVisibility(View.GONE);
        } else {
            holder.gridView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class GroupViewHolder {
        public View layout;
        public TextView tv1;
        public TextView tv2;
        public TextView tv3;
        public TextView tv4;
        public ImageView ivArrow;
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
                if (null == listBean) {
                    layout.setVisibility(View.INVISIBLE);
                } else {
                    layout.setVisibility(View.VISIBLE);
                    tvName.setText(listBean.getPlayName());
                    tvOdds.setText(listBean.getBonus());
                    if (mCheckedList.contains(listBean)) {
                        layout.setBackgroundResource(R.drawable.liuhecai_btn_xuanzhong_02);
                    } else {
                        layout.setBackgroundResource(R.drawable.liuhecai_btn_weixuan_01);
                    }
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
                View layout = ViewHolder.get(convertView, R.id.layout);
                TextView tvName = ViewHolder.get(convertView, R.id.tv_name);
                EditText etNum = ViewHolder.get(convertView, R.id.et_num);
                BuyRoomBean.PlayDetailListBean.ListBean listBean = mList.get(position);
                if (null == listBean) {
                    layout.setVisibility(View.INVISIBLE);
                } else {
                    layout.setVisibility(View.VISIBLE);
                    SpannableString ss = new SpannableString(listBean.getPlayName() + " " + listBean.getBonus());
                    int start = listBean.getPlayName().length() + 1;
                    ss.setSpan(new ForegroundColorSpan(Color.parseColor("#C30D23")), start, start+listBean.getBonus().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvName.setText(ss);

                    etNum.setTag(R.id.buy_data, listBean);
                    if (mNumberMap.containsKey(listBean)) {
                        etNum.setText(mNumberMap.get(listBean));
                    } else {
                        etNum.setText("");
                    }
                }
                return convertView;
            }
        }
    }
}
