package com.example.admin.caipiao33.fragment.adapter;

import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.NumberInputFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 前四个四个折叠的适配器
 */

public class TypeSixAdapter extends MyBaseBuyAdapter
{
    private static final int COUNT = 1;
    private BuyRoomBean mBuyRoomBean;
    private final LayoutInflater mInflater;
    private final int mType;
    private List<BuyRoomBean.PlayDetailListBean.ListBean> mCheckedList = new ArrayList<>();
    private List<BeanGroup> mDataList;
    public static final List<String> NUMBER_RED = Arrays.asList("01", "02", "07", "08", "12", "13", "18", "19", "23", "24", "29", "30", "34", "35", "40", "45", "46");
    public static final List<String> NUMBER_GREEN = Arrays.asList("03", "04", "09", "10", "14", "15", "20", "25", "26", "31", "36", "37", "41", "42", "47", "48");
    public static final List<String> NUMBER_BLUE = Arrays.asList("05", "06", "11", "16", "17", "21", "22", "27", "28", "32", "33", "38", "39", "43", "44", "49");

    public TypeSixAdapter(LayoutInflater inflater, BuyRoomBean bean, int type) {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
        this.mType = type;
        updateData(bean);

    }

    /**
     * 重置数据
     * @param bean
     */
    public void updateData(BuyRoomBean bean) {
        this.mBuyRoomBean = bean;
        creatDataNormal();
    }

    private void creatDataNormal()
    {
        int totalSize = mBuyRoomBean.getPlayDetailList().size();
        mDataList = new ArrayList<>(totalSize);
        for (int i =0; i < totalSize; i++) {
            BeanGroup beanGroup = new BeanGroup();
            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(i);
            List<String> groupNameList = new ArrayList<>(1);
            groupNameList.add(playDetailListBean.getName());
            beanGroup.setGroupNameList(groupNameList);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            int childListSize;
            if (list.size() % COUNT == 0) {
                childListSize = list.size() / COUNT;
            } else {
                childListSize = list.size() / COUNT + 1;
            }
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);
            for (int j = 0; j < childListSize; j++) {
                List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT);
                for (int k = 0; k < COUNT; k++) {
                    int index = j * COUNT + k;
                    if (index >= list.size()) {
                        break;
                    }
                    BuyRoomBean.PlayDetailListBean.ListBean e = list.get(index);
                    e.setParentName(playDetailListBean.getName());
                    itemList.add(e);
                }
                childList.add(itemList);
            }
            beanGroup.setChildList(childList);
            mDataList.add(beanGroup);
        }
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
    }

    /**
     * 获取已选中的列表
     * @return
     */
    @Override
    public List<BuyRoomBean.PlayDetailListBean.ListBean> getCheckedList()
    {
        return mCheckedList;
    }

    @Override
    public int getGroupCount()
    {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return mDataList.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group_six, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        // 自选下注的类型
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_self_select_six, null);
            InputFilter[] filters = {new NumberInputFilter()};
            final EditText etNum = ViewHolder.get(convertView, R.id.et_num);
            etNum.setFilters(filters);
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
                    BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) etNum
                            .getTag(R.id.buy_data);
                    if (TextUtils.isEmpty(s1))
                    {
                        // 如果最新值为空，直接移除该项目
                        if (mCheckedList.contains(listBean))
                        {
                            mCheckedList.remove(listBean);
                        }
                        // 原来没有保存值，最新的值也是为空的话就直接忽略
                        return;
                    }
                    listBean.setMoney(s1);
                    if (mCheckedList.contains(listBean))
                    {
                        mCheckedList.remove(listBean);
                    }
                    mCheckedList.add(listBean);
                }
            });
        }
        TextView tvNum = ViewHolder.get(convertView, R.id.tv_num);
        TextView tv2 = ViewHolder.get(convertView, R.id.tv_2);
        EditText etNum = ViewHolder.get(convertView, R.id.et_num);
        etNum.setTag(R.id.buy_data, null);
        List<BuyRoomBean.PlayDetailListBean.ListBean> listBeen = mDataList.get(groupPosition)
                .getChildList()
                .get(childPosition);
        for (int i = 0; i < listBeen.size(); i++)
        {
            BuyRoomBean.PlayDetailListBean.ListBean listBean = listBeen.get(i);
            String playName = listBean.getPlayName();
            if (NUMBER_RED.contains(playName)) {
                tvNum.setBackgroundResource(R.drawable.shape_circle_red);
            } else if (NUMBER_GREEN.contains(playName)){
                tvNum.setBackgroundResource(R.drawable.shape_circle_green);
            } else if (NUMBER_BLUE.contains(playName)) {
                tvNum.setBackgroundResource(R.drawable.shape_circle_blue);
            } else {
                tvNum.setBackgroundDrawable(null);
            }
            tvNum.setText(playName);
            tv2.setText(listBean.getBonus());
            etNum.setTag(R.id.buy_data, listBean);
            if (mCheckedList.contains(listBean))
            {
                etNum.setText(listBean.getMoney());
            }
            else
            {
                etNum.setText("");
            }
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}
