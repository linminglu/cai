package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.utils.Combine;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 连肖连尾的适配器
 */

public class TypeSix27Adapter extends TypeBeforeAdapter
{
    private static final int COUNT = 4;
    // 区分是 二连、三连、四连、五连
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList1 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList2 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList3 = new ArrayList<>();
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList4 = new ArrayList<>();

    private boolean isLX = false;
    private List<BuyRoomBean.PlayDetailListBean> mPlayDetailList;

    public TypeSix27Adapter(LayoutInflater inflater, BuyRoomBean bean, int type)
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
        if (mType == QuickBuyFragment.TYPE_QUICK)
        {
            isLX = false;
            // 连尾
            mPlayDetailList = mBuyRoomBean.getPlayDetailList();
            if (null != mPlayDetailList && mPlayDetailList.size() == 8)
            {
                Iterator<BuyRoomBean.PlayDetailListBean> iterator = mPlayDetailList.iterator();
                int temp = 0;
                while (iterator.hasNext())
                {
                    iterator.next();
                    if (temp < 4)
                    {
                        iterator.remove();
                    }
                    temp++;
                }
            }
        }
        else
        {
            isLX = true;
            mType = QuickBuyFragment.TYPE_QUICK;
            // 连肖
            mPlayDetailList = mBuyRoomBean.getPlayDetailList();
            if (null != mPlayDetailList && mPlayDetailList.size() == 8)
            {
                Iterator<BuyRoomBean.PlayDetailListBean> iterator = mPlayDetailList.iterator();
                int temp = 0;
                while (iterator.hasNext())
                {
                    iterator.next();
                    if (temp >= 4)
                    {
                        iterator.remove();
                    }
                    temp++;
                }
            }
        }
        creatDataNormal();
    }

    private void creatDataNormal()
    {
        int totalSize = mPlayDetailList.size();
        mDataList = new ArrayList<>(totalSize);
        for (int i = 0; i < totalSize; i++)
        {
            BeanGroup beanGroup = new BeanGroup();
            BuyRoomBean.PlayDetailListBean playDetailListBean = mPlayDetailList.get(i);
            List<String> groupNameList = new ArrayList<>(1);
            groupNameList.add(playDetailListBean.getName());
            beanGroup.setGroupNameList(groupNameList);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            int childListSize;
            if (list.size() % COUNT == 0)
            {
                childListSize = list.size() / COUNT;
            }
            else
            {
                childListSize = list.size() / COUNT + 1;
            }
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);
            for (int j = 0; j < childListSize; j++)
            {
                List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT);
                for (int k = 0; k < COUNT; k++)
                {
                    int index = j * COUNT + k;
                    if (index >= list.size())
                    {
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
        return super.getGroupView(groupPosition, isExpanded, convertView, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        return super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
    }

    /**
     * 清空已选中的内容
     */
    @Override
    public void clearChecked()
    {
        if (mCheckedList.size() > 0)
        {
            mCheckedList.clear();
            notifyDataSetChanged();
        }
        checkedList1.clear();
        checkedList2.clear();
        checkedList3.clear();
        checkedList4.clear();
    }

    /**
     * 获取已选中的列表
     *
     * @return
     */
    @Override
    public List<BuyRoomBean.PlayDetailListBean.ListBean> getCheckedList()
    {
        // 区分是二连、三连、四连、五连
        int size1 = checkedList1.size();
        if (size1 > 0 && size1 < 2)
        {
            ToastUtil.show(isLX ? "二连肖下注号码有误！" : "二连尾下注号码有误！");
            return null;
        }
        int size2 = checkedList2.size();
        if (size2 > 0 && size2 < 3)
        {
            ToastUtil.show(isLX ? "三连肖下注号码有误！" : "三连尾下注号码有误！");
            return null;
        }
        int size3 = checkedList3.size();
        if (size3 > 0 && size3 < 4)
        {
            ToastUtil.show(isLX ? "四连肖下注号码有误！" : "四连尾下注号码有误！");
            return null;
        }
        int size4 = checkedList4.size();
        if (size4 > 0 && size4 < 5)
        {
            ToastUtil.show(isLX ? "五连肖下注号码有误！" : "五连尾下注号码有误！");
            return null;
        }

        // 寻找已选中玩法的具体对象
        List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList = new ArrayList<>();
        if (size1 > 0)
        {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 2, checkedList1);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mPlayDetailList.get(0);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for (List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result)
            {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++)
                {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0)
                    {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (size2 > 0)
        {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 3, checkedList2);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mPlayDetailList.get(1);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for (List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result)
            {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++)
                {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0)
                    {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (checkedList3.size() > 0)
        {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 4, checkedList3);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mPlayDetailList.get(2);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for (List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result)
            {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++)
                {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0)
                    {
                        sb.append(" & ");
                    }
                    sb.append(bean.getPlayName());
                }
                clone.setPlayName(sb.toString());
                checkedList.add(clone);
            }
        }
        if (size4 > 0)
        {
            // 组合
            Combine<BuyRoomBean.PlayDetailListBean.ListBean> c = new Combine<>();
            c.combine(0, 5, checkedList4);
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> result = c.getResult();

            BuyRoomBean.PlayDetailListBean playDetailListBean = mPlayDetailList.get(3);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            BuyRoomBean.PlayDetailListBean.ListBean listBean = list.get(list.size() - 1);

            for (List<BuyRoomBean.PlayDetailListBean.ListBean> tmp : result)
            {
                BuyRoomBean.PlayDetailListBean.ListBean clone = listBean.clone();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < tmp.size(); i++)
                {
                    BuyRoomBean.PlayDetailListBean.ListBean bean = tmp.get(i);
                    if (i != 0)
                    {
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
        switch (v.getId())
        {
            case R.id.layout1:
            case R.id.layout2:
            case R.id.layout3:
            case R.id.layout4:
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) v
                        .getTag(R.id.buy_data);
                if (null == listBean)
                {
                    return;
                }
                if (mCheckedList.contains(listBean))
                {
                    mCheckedList.remove(listBean);
                    String parentName = listBean.getParentName();
                    if (isLX && parentName.contains("二连肖") || !isLX && parentName.contains("二连尾"))
                    {
                        checkedList1.remove(listBean);
                    }
                    else if (isLX && parentName.contains("三连肖") || !isLX && parentName.contains("三连尾"))
                    {
                        checkedList2.remove(listBean);
                    }
                    else if (isLX && parentName.contains("四连肖") || !isLX && parentName.contains("四连尾"))
                    {
                        checkedList3.remove(listBean);
                    }
                    else if (isLX && parentName.contains("五连肖") || !isLX && parentName.contains("五连尾"))
                    {
                        checkedList4.remove(listBean);
                    }
                }
                else
                {
                    String parentName = listBean.getParentName();
                    mCheckedList.add(listBean);
                    if (isLX && parentName.contains("二连肖") || !isLX && parentName.contains("二连尾"))
                    {
                        checkedList1.add(listBean);
                    }
                    else if (isLX && parentName.contains("三连肖") || !isLX && parentName.contains("三连尾"))
                    {
                        checkedList2.add(listBean);
                    }
                    else if (isLX && parentName.contains("四连肖") || !isLX && parentName.contains("四连尾"))
                    {
                        checkedList3.add(listBean);
                    }
                    else if (isLX && parentName.contains("五连肖") || !isLX && parentName.contains("五连尾"))
                    {
                        checkedList4.add(listBean);
                    }
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
