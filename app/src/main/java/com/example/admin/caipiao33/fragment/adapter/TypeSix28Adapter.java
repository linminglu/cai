package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.Combine;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 6合连码的适配器
 */

public class TypeSix28Adapter extends TypeBeforeAdapter
{
    private static final int COUNT = 4;
    // 区分是3中2、3全中、2全中、2中特、特串,分别对应1、2、3、4、5
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList1 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList2 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList3 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList4 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList5 = new ArrayList<>();


    public TypeSix28Adapter(LayoutInflater inflater, BuyRoomBean bean, int type) {
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
            if (playDetailListBean.getName().equals("四全中")) {
                break;
            }
            List<String> groupNameList = new ArrayList<>(2);
            groupNameList.add(playDetailListBean.getName());
            StringBuilder sb = new StringBuilder();
            for(BuyRoomBean.PlayDetailListBean.ListBean bean : playDetailListBean.getList()) {
                sb.append(bean.getBonus()).append("/");
            }
            groupNameList.add(sb.deleteCharAt(sb.length() - 1).toString());
            beanGroup.setGroupNameList(groupNameList);
            int childListSize = 49 / COUNT + 1;
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);
            for (int j = 0; j < childListSize; j++) {
                List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT);
                for (int k = 0; k < COUNT; k++) {
                    int index = j * COUNT + k;
                    if (index >= 49) {
                        break;
                    }
                    BuyRoomBean.PlayDetailListBean.ListBean e = new BuyRoomBean.PlayDetailListBean.ListBean();
                    String playName = String.valueOf(index + 1);
                    e.setPlayName(playName);
                    // 用来支持包含查找，后续获得已选list中，不能使用
                    e.setPlayId(playDetailListBean.getName() + ";" + playName);
                    itemList.add(e);
                }
                childList.add(itemList);
            }
            beanGroup.setChildList(childList);
            mDataList.add(beanGroup);
        }
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
            convertView = mInflater.inflate(R.layout.item_buy_quick_group, null);
        }

        TextView tv1 = ViewHolder.get(convertView, R.id.tv_1);
        TextView tv2 = ViewHolder.get(convertView, R.id.tv_2);
        TextView tv3 = ViewHolder.get(convertView, R.id.tv_3);
        TextView tv4 = ViewHolder.get(convertView, R.id.tv_4);
        ImageView ivArrow = ViewHolder.get(convertView, R.id.iv_arrow);

        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");
        if (isExpanded) {
            ivArrow.setVisibility(View.GONE);
        } else {
            ivArrow.setVisibility(View.VISIBLE);
        }
        BeanGroup beanGroup = mDataList.get(groupPosition);
        List<String> groupNameList = beanGroup.getGroupNameList();
        for (int i = 0; i < groupNameList.size(); i++) {
            switch (i) {
                case 0:
                    tv1.setText(groupNameList.get(i));
                    break;
                case 1:
                    tv2.setText(groupNameList.get(i));
                    break;
                case 2:
                    tv3.setText(groupNameList.get(i));
                    break;
                case 3:
                    tv4.setText(groupNameList.get(i));
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        // 快捷下注的类型
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_buy_quick_26, null);
            ViewHolder.get(convertView, R.id.layout1).setOnClickListener(this);
            ViewHolder.get(convertView, R.id.layout2).setOnClickListener(this);
            ViewHolder.get(convertView, R.id.layout3).setOnClickListener(this);
            ViewHolder.get(convertView, R.id.layout4).setOnClickListener(this);
        }
        View layout1 = ViewHolder.get(convertView, R.id.layout1);
        TextView tvName1 = ViewHolder.get(convertView, R.id.tv_name1);
        View layout2 = ViewHolder.get(convertView, R.id.layout2);
        TextView tvName2 = ViewHolder.get(convertView, R.id.tv_name2);
        View layout3 = ViewHolder.get(convertView, R.id.layout3);
        TextView tvName3 = ViewHolder.get(convertView, R.id.tv_name3);
        View layout4 = ViewHolder.get(convertView, R.id.layout4);
        TextView tvName4 = ViewHolder.get(convertView, R.id.tv_name4);

        layout1.setVisibility(View.INVISIBLE);
        layout1.setTag(R.id.buy_data, null);
        layout2.setVisibility(View.INVISIBLE);
        layout2.setTag(R.id.buy_data, null);
        layout3.setVisibility(View.INVISIBLE);
        layout3.setTag(R.id.buy_data, null);
        layout4.setVisibility(View.INVISIBLE);
        layout4.setTag(R.id.buy_data, null);

        List<BuyRoomBean.PlayDetailListBean.ListBean> listBeen = mDataList.get(groupPosition)
                .getChildList()
                .get(childPosition);
        for (int i = 0; i < listBeen.size(); i++) {
            BuyRoomBean.PlayDetailListBean.ListBean listBean = listBeen.get(i);
            View layout = null;
            TextView tvName = null;
            switch (i) {
                case 0:
                    layout = layout1;
                    tvName = tvName1;
                    break;
                case 1:
                    layout = layout2;
                    tvName = tvName2;
                    break;
                case 2:
                    layout = layout3;
                    tvName = tvName3;
                    break;
                case 3:
                    layout = layout4;
                    tvName = tvName4;
                    break;
                default:
                    break;
            }
            layout.setVisibility(View.VISIBLE);
            layout.setTag(R.id.buy_data, listBean);
            tvName.setText(listBean.getPlayName());
            if (mCheckedList.contains(listBean)) {
                tvName.setBackgroundResource(R.drawable.shape_circle_red);
                tvName.setTextColor(mInflater.getContext().getResources().getColor(R.color.white));
            } else {
                tvName.setBackgroundResource(R.drawable.shape_circle_red_hollow);
                tvName.setTextColor(mInflater.getContext().getResources().getColor(R.color.red));
            }
        }
        return convertView;
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
        checkedList1.clear();
        checkedList2.clear();
        checkedList3.clear();
        checkedList4.clear();
        checkedList5.clear();
    }

    /**
     * 获取已选中的列表
     * @return
     */
    @Override
    public List<BuyRoomBean.PlayDetailListBean.ListBean> getCheckedList()
    {
        // 区分是3中2、3全中、2全中、2中特、特串
        int size1 = checkedList1.size();
        if (size1 > 0 && size1 < 3) {
            ToastUtil.show("三中二下注号码有误！");
            return null;
        }
        int size2 = checkedList2.size();
        if (size2 > 0 && size2 < 3) {
            ToastUtil.show("三全中下注号码有误！");
            return null;
        }
        int size3 = checkedList3.size();
        if (size3 > 0 && size3 < 2) {
            ToastUtil.show("二全中下注号码有误！");
            return null;
        }
        int size4 = checkedList4.size();
        if (size4 > 0 && size4 < 2) {
            ToastUtil.show("二中特下注号码有误！");
            return null;
        }
        int size5 = checkedList5.size();
        if (size5 > 0 && size5 < 2) {
            ToastUtil.show("特串下注号码有误！");
            return null;
        }

        // 排序
        if (size1 > 0) {
            Collections.sort(checkedList1, new Comparator<BuyRoomBean.PlayDetailListBean.ListBean>()
            {
                @Override
                public int compare(BuyRoomBean.PlayDetailListBean.ListBean o1, BuyRoomBean.PlayDetailListBean.ListBean o2)
                {
                    int intO1PlayName = Integer.valueOf(o1.getPlayName());
                    int intO2PlayName = Integer.valueOf(o2.getPlayName());
                    return intO1PlayName > intO2PlayName ? 1 : intO1PlayName < intO2PlayName ? -1 : 0;
                }
            });
        }
        if (size2 > 0) {
            Collections.sort(checkedList2, new Comparator<BuyRoomBean.PlayDetailListBean.ListBean>()
            {
                @Override
                public int compare(BuyRoomBean.PlayDetailListBean.ListBean o1, BuyRoomBean.PlayDetailListBean.ListBean o2)
                {
                    int intO1PlayName = Integer.valueOf(o1.getPlayName());
                    int intO2PlayName = Integer.valueOf(o2.getPlayName());
                    return intO1PlayName > intO2PlayName ? 1 : intO1PlayName < intO2PlayName ? -1 : 0;
                }
            });
        }
        if (checkedList3.size() > 0) {
            Collections.sort(checkedList3, new Comparator<BuyRoomBean.PlayDetailListBean.ListBean>()
            {
                @Override
                public int compare(BuyRoomBean.PlayDetailListBean.ListBean o1, BuyRoomBean.PlayDetailListBean.ListBean o2)
                {
                    int intO1PlayName = Integer.valueOf(o1.getPlayName());
                    int intO2PlayName = Integer.valueOf(o2.getPlayName());
                    return intO1PlayName > intO2PlayName ? 1 : intO1PlayName < intO2PlayName ? -1 : 0;
                }
            });
        }
        if (size4 > 0) {
            Collections.sort(checkedList4, new Comparator<BuyRoomBean.PlayDetailListBean.ListBean>()
            {
                @Override
                public int compare(BuyRoomBean.PlayDetailListBean.ListBean o1, BuyRoomBean.PlayDetailListBean.ListBean o2)
                {
                    int intO1PlayName = Integer.valueOf(o1.getPlayName());
                    int intO2PlayName = Integer.valueOf(o2.getPlayName());
                    return intO1PlayName > intO2PlayName ? 1 : intO1PlayName < intO2PlayName ? -1 : 0;
                }
            });
        }
        if (size5 > 0) {
            Collections.sort(checkedList5, new Comparator<BuyRoomBean.PlayDetailListBean.ListBean>()
            {
                @Override
                public int compare(BuyRoomBean.PlayDetailListBean.ListBean o1, BuyRoomBean.PlayDetailListBean.ListBean o2)
                {
                    int intO1PlayName = Integer.valueOf(o1.getPlayName());
                    int intO2PlayName = Integer.valueOf(o2.getPlayName());
                    return intO1PlayName > intO2PlayName ? 1 : intO1PlayName < intO2PlayName ? -1 : 0;
                }
            });
        }

        // 寻找已选中玩法的具体对象
        List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList = new ArrayList<>();
        if (size1 > 0) {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 3, checkedList1);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(0);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for(List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result) {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                clone.setParentName(playDetailListBean.getName());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++) {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0) {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (size2 > 0) {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 3, checkedList2);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(1);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for(List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result) {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                clone.setParentName(playDetailListBean.getName());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++) {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0) {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (checkedList3.size() > 0) {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 2, checkedList3);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(2);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for(List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result) {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                clone.setParentName(playDetailListBean.getName());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++) {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0) {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (size4 > 0) {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 2, checkedList4);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(3);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for(List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result) {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                clone.setParentName(playDetailListBean.getName());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++) {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0) {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (size5 > 0) {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 2, checkedList5);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(4);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for(List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result) {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                clone.setParentName(playDetailListBean.getName());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++) {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0) {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        return checkedList;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.layout1:
            case R.id.layout2:
            case R.id.layout3:
            case R.id.layout4:
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) v.getTag(R.id.buy_data);
                if (null == listBean) {
                    return;
                }
                if (mCheckedList.contains(listBean)) {
                    mCheckedList.remove(listBean);
                    String playId = listBean.getPlayId();
                    if (playId.contains("三中二")) {
                        checkedList1.remove(listBean);
                    } else if (playId.contains("三全中")) {
                        checkedList2.remove(listBean);
                    } else if (playId.contains("二全中")) {
                        checkedList3.remove(listBean);
                    } else if (playId.contains("二中特")) {
                        checkedList4.remove(listBean);
                    } else if (playId.contains("特串")) {
                        checkedList5.remove(listBean);
                    }
                } else {
                    String playId = listBean.getPlayId();
                    if ((playId.contains("三中二") && checkedList1.size() >= 7)
                            || playId.contains("三全中") && checkedList2.size() >= 7
                            || playId.contains("二全中") && checkedList3.size() >= 7
                            || playId.contains("二中特") && checkedList4.size() >= 7
                            || playId.contains("特串") && checkedList5.size() >= 7) {
                        ToastUtil.show("一种类型最多选择7个！");
                        return;
                    }
                    mCheckedList.add(listBean);
                    if (playId.contains("三中二")) {
                        checkedList1.add(listBean);
                    } else if (playId.contains("三全中")) {
                        checkedList2.add(listBean);
                    } else if (playId.contains("二全中")) {
                        checkedList3.add(listBean);
                    } else if (playId.contains("二中特")) {
                        checkedList4.add(listBean);
                    } else if (playId.contains("特串")) {
                        checkedList5.add(listBean);
                    }
                }

                LinearLayout layout = (LinearLayout) v;
                TextView tvName = (TextView) layout.getChildAt(0);
                if (mCheckedList.contains(listBean)) {
                    tvName.setBackgroundResource(R.drawable.shape_circle_red);
                    tvName.setTextColor(mInflater.getContext().getResources().getColor(R.color.white));
                } else {
                    tvName.setBackgroundResource(R.drawable.shape_circle_red_hollow);
                    tvName.setTextColor(mInflater.getContext().getResources().getColor(R.color.red));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}
