package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 合肖的适配器
 */

public class TypeSix6Adapter extends TypeBeforeAdapter
{
    private static final int COUNT = 3;

    public TypeSix6Adapter(LayoutInflater inflater, BuyRoomBean bean, int type)
    {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
        this.mType = type;
        updateData(bean);
    }

    /**
     * 重置数据
     *
     * @param bean
     */
    public void updateData(BuyRoomBean bean)
    {
        this.mBuyRoomBean = bean;
        creatDataNormal();

    }

    private void creatDataNormal()
    {
        int totalSize = mBuyRoomBean.getPlayDetailList().size();
        mDataList = new ArrayList<>(totalSize);
        BeanGroup beanGroup = new BeanGroup();
        List<String> groupNameList = new ArrayList<>(1);
        groupNameList.add(mBuyRoomBean.getPlayName());
        beanGroup.setGroupNameList(groupNameList);
        BuyRoomBean.SxNamesBean sxNames = mBuyRoomBean.getSxNames();
        int childListSize = 12 / COUNT;
        List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);

        for (int j = 0; j < childListSize; j++)
        {
            List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT);
            for (int k = 0; k < COUNT; k++)
            {
                int index = j * COUNT + k;
                if (index >= 12)
                {
                    break;
                }
                BuyRoomBean.PlayDetailListBean.ListBean e = new BuyRoomBean.PlayDetailListBean.ListBean();
                switch (index)
                {
                    case 0:
                        e.setPlayName("鼠");
                        e.setBonus(sxNames.get鼠().replaceAll(",", " "));
                        e.setPlayId("鼠");
                        break;
                    case 1:
                        e.setPlayName("牛");
                        e.setBonus(sxNames.get牛().replaceAll(",", " "));
                        e.setPlayId("牛");
                        break;
                    case 2:
                        e.setPlayName("虎");
                        e.setBonus(sxNames.get虎().replaceAll(",", " "));
                        e.setPlayId("虎");
                        break;
                    case 3:
                        e.setPlayName("兔");
                        e.setBonus(sxNames.get兔().replaceAll(",", " "));
                        e.setPlayId("兔");
                        break;
                    case 4:
                        e.setPlayName("龙");
                        e.setBonus(sxNames.get龙().replaceAll(",", " "));
                        e.setPlayId("龙");
                        break;
                    case 5:
                        e.setPlayName("蛇");
                        e.setBonus(sxNames.get蛇().replaceAll(",", " "));
                        e.setPlayId("蛇");
                        break;
                    case 6:
                        e.setPlayName("马");
                        e.setBonus(sxNames.get马().replaceAll(",", " "));
                        e.setPlayId("马");
                        break;
                    case 7:
                        e.setPlayName("羊");
                        e.setBonus(sxNames.get羊().replaceAll(",", " "));
                        e.setPlayId("羊");
                        break;
                    case 8:
                        e.setPlayName("猴");
                        e.setBonus(sxNames.get猴().replaceAll(",", " "));
                        e.setPlayId("猴");
                        break;
                    case 9:
                        e.setPlayName("鸡");
                        e.setBonus(sxNames.get鸡().replaceAll(",", " "));
                        e.setPlayId("鸡");
                        break;
                    case 10:
                        e.setPlayName("狗");
                        e.setBonus(sxNames.get狗().replaceAll(",", " "));
                        e.setPlayId("狗");
                        break;
                    case 11:
                        e.setPlayName("猪");
                        e.setBonus(sxNames.get猪().replaceAll(",", " "));
                        e.setPlayId("猪");
                        break;
                }
                itemList.add(e);
            }
            childList.add(itemList);
        }
        beanGroup.setChildList(childList);
        mDataList.add(beanGroup);
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
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group_6, null);
        }

        TextView tv1 = ViewHolder.get(convertView, R.id.tv_1);
        TextView tv2 = ViewHolder.get(convertView, R.id.tv_2);
        TextView tv3 = ViewHolder.get(convertView, R.id.tv_3);
        ImageView ivArrow = ViewHolder.get(convertView, R.id.iv_arrow);

        tv1.setText("");
        tv2.setText("");
        tv3.setText("");
        if (isExpanded)
        {
            ivArrow.setVisibility(View.GONE);
        }
        else
        {
            ivArrow.setVisibility(View.VISIBLE);
        }
        BeanGroup beanGroup = mDataList.get(groupPosition);
        List<String> groupNameList = beanGroup.getGroupNameList();
        for (int i = 0; i < groupNameList.size(); i++)
        {
            switch (i)
            {
                case 0:
                    tv1.setText(groupNameList.get(i));
                    break;
                case 1:
                    tv2.setText(groupNameList.get(i));
                    break;
                case 2:
                    tv3.setText(groupNameList.get(i));
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
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_quick_6, null);
            ViewHolder.get(convertView, R.id.layout1).setOnClickListener(this);
            ViewHolder.get(convertView, R.id.layout2).setOnClickListener(this);
            ViewHolder.get(convertView, R.id.layout3).setOnClickListener(this);
        }
        View layout1 = ViewHolder.get(convertView, R.id.layout1);
        TextView tvName1 = ViewHolder.get(convertView, R.id.tv_name1);
        TextView tvOdds1 = ViewHolder.get(convertView, R.id.tv_odds1);
        View layout2 = ViewHolder.get(convertView, R.id.layout2);
        TextView tvName2 = ViewHolder.get(convertView, R.id.tv_name2);
        TextView tvOdds2 = ViewHolder.get(convertView, R.id.tv_odds2);
        View layout3 = ViewHolder.get(convertView, R.id.layout3);
        TextView tvName3 = ViewHolder.get(convertView, R.id.tv_name3);
        TextView tvOdds3 = ViewHolder.get(convertView, R.id.tv_odds3);

        layout1.setVisibility(View.INVISIBLE);
        layout1.setTag(R.id.buy_data, null);
        layout2.setVisibility(View.INVISIBLE);
        layout2.setTag(R.id.buy_data, null);
        layout3.setVisibility(View.INVISIBLE);
        layout3.setTag(R.id.buy_data, null);

        List<BuyRoomBean.PlayDetailListBean.ListBean> listBeen = mDataList.get(groupPosition)
                .getChildList()
                .get(childPosition);
        for (int i = 0; i < listBeen.size(); i++)
        {
            BuyRoomBean.PlayDetailListBean.ListBean listBean = listBeen.get(i);
            View layout = null;
            TextView tvName = null;
            TextView tvOdds = null;
            switch (i)
            {
                case 0:
                    layout = layout1;
                    tvName = tvName1;
                    tvOdds = tvOdds1;
                    break;
                case 1:
                    layout = layout2;
                    tvName = tvName2;
                    tvOdds = tvOdds2;
                    break;
                case 2:
                    layout = layout3;
                    tvName = tvName3;
                    tvOdds = tvOdds3;
                    break;
                default:
                    break;
            }
            layout.setVisibility(View.VISIBLE);
            layout.setTag(R.id.buy_data, listBean);
            tvName.setText(listBean.getPlayName());
            tvOdds.setText(listBean.getBonus());
            if (mCheckedList.contains(listBean))
            {
                layout.setBackgroundResource(R.drawable.liuhecai_btn_xuanzhong_02);
            }
            else
            {
                layout.setBackgroundResource(R.drawable.liuhecai_btn_weixuan_01);
            }
        }
        return convertView;
    }

    /**
     * 获取已选中的列表
     *
     * @return
     */
    @Override
    public List<BuyRoomBean.PlayDetailListBean.ListBean> getCheckedList()
    {
        if (mCheckedList.size() < 2)
        {
            ToastUtil.show("下注号码有误，请检查！");
            return null;
        }

        // 寻找已选中玩法的具体对象
        BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList().get(0);
        List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
        BuyRoomBean.PlayDetailListBean.ListBean clone = list.get(mCheckedList.size() - 2).clone();
        List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList = new ArrayList<>();
        clone.setParentName(clone.getPlayName());
        StringBuilder sb = new StringBuilder();
        int size = mCheckedList.size();
        for (int i = 0; i < size; i++)
        {
            BuyRoomBean.PlayDetailListBean.ListBean bean = mCheckedList.get(i);
            if (i != 0)
            {
                sb.append(" & ");
            }
            sb.append(bean.getPlayName());
        }
        clone.setPlayName(sb.toString());
        checkedList.add(clone);
        return checkedList;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.layout1:
            case R.id.layout2:
            case R.id.layout3:
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) v
                        .getTag(R.id.buy_data);
                if (null == listBean)
                {
                    return;
                }
                if (mCheckedList.contains(listBean))
                {
                    mCheckedList.remove(listBean);
                }
                else
                {
                    if (mCheckedList.size() >= 11)
                    {
                        ToastUtil.show("最多选择11个！");
                        return;
                    }
                    mCheckedList.add(listBean);
                }
                if (mCheckedList.contains(listBean))
                {
                    v.setBackgroundResource(R.drawable.liuhecai_btn_xuanzhong_02);
                }
                else
                {
                    v.setBackgroundResource(R.drawable.liuhecai_btn_weixuan_01);
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
