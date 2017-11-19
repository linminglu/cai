package com.example.admin.caipiao33.fragment.adapter;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.utils.ViewHolder;
import com.example.admin.caipiao33.views.NumberInputFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 前四个四个折叠的适配器
 */

public class TypeSixAdapter extends TypeBeforeAdapter
{
    private static final int COUNT1 = 1;
    public static final List<String> NUMBER_RED = Arrays.asList("01", "02", "07", "08", "12", "13", "18", "19", "23", "24", "29", "30", "34", "35", "40", "45", "46", "1", "2", "7", "8");
    public static final List<String> NUMBER_BLUE = Arrays.asList("03", "04", "09", "10", "14", "15", "20", "25", "26", "31", "36", "37", "41", "42", "47", "48", "3", "4", "9");
    public static final List<String> NUMBER_GREEN = Arrays.asList("05", "06", "11", "16", "17", "21", "22", "27", "28", "32", "33", "38", "39", "43", "44", "49", "5", "6");
    private String mPlayName;
    private String mPlayId;
    // 是否当前类处理，false就交给父类处理
    private boolean isCurrExecute = true;
    // 用来区分是当前这种类型的view，注意和父类中的区别，表示不能复用的类型
    private final int GROUP_VIEW_TYPE = 1003;
    // 用来区分是当前这种类型的view，注意和父类中的区别，表示不能复用的类型
    private final int CHILD_VIEW_TYPE = 1004;

    /**
     * 1 -- B （备注：取前49个数据）
     * 2 -- A
     * 8 -- 正码
     * 9 -- 正一
     * 10 -- 正二
     * 11 -- 正三
     * 12 -- 正四
     * 13 -- 正五
     * 14 -- 正六
     */
    private static final List<String> CURR_EXECUTE = Arrays.asList("1", "2", "8", "9", "10", "11", "12", "13", "14");

    public TypeSixAdapter(LayoutInflater inflater, BuyRoomBean bean, int type)
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
        mPlayName = bean.getPlayName();
        List<BuyRoomBean.PlayListBean> playList = bean.getPlayList();
        for (BuyRoomBean.PlayListBean beanPlayList : playList)
        {
            String playName = beanPlayList.getPlayName();
            if (mPlayName.equals(playName))
            {
                mPlayId = beanPlayList.getPlayId();
                break;
            }
        }
        if (CURR_EXECUTE.contains(mPlayId) && mType == QuickBuyFragment.TYPE_SELF_SELECT)
        {
            isCurrExecute = true;
            creatDataNormal();
        }
        else
        {
            isCurrExecute = false;
            super.updateData(this.mBuyRoomBean);
        }

    }

    private void creatDataNormal()
    {
        int totalSize = mBuyRoomBean.getPlayDetailList().size();
        mDataList = new ArrayList<>(totalSize);
        for (int i = 0; i < totalSize; i++)
        {
            BeanGroup beanGroup = new BeanGroup();
            BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                    .get(i);
            List<String> groupNameList = new ArrayList<>(1);
            groupNameList.add(playDetailListBean.getName());
            beanGroup.setGroupNameList(groupNameList);
            List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
            int childListSize;
            if (list.size() % COUNT1 == 0)
            {
                childListSize = list.size() / COUNT1;
            }
            else
            {
                childListSize = list.size() / COUNT1 + 1;
            }
            List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);
            for (int j = 0; j < childListSize; j++)
            {
                List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT1);
                for (int k = 0; k < COUNT1; k++)
                {
                    int index = j * COUNT1 + k;
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
        if (!isCurrExecute || mType == QuickBuyFragment.TYPE_QUICK)
        {
            // 父类处理
            return super.getGroupView(groupPosition, isExpanded, convertView, parent);
        }
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group_six, null);
        }
        else
        {
            Object tag = convertView.getTag(R.id.buy_view_type);
            if (null == tag || (int) tag != GROUP_VIEW_TYPE)
            {
                convertView = mInflater.inflate(R.layout.item_buy_quick_group_six, null);
            }
        }
        convertView.setTag(R.id.buy_view_type, GROUP_VIEW_TYPE);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (!isCurrExecute || mType == QuickBuyFragment.TYPE_QUICK)
        {
            // 父类处理
            return super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        }
        // 自选下注的类型
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_self_select_six, null);
            initSixSelfContentView(convertView);
        }
        else
        {
            Object tag = convertView.getTag(R.id.buy_view_type);
            if (null == tag || (int) tag != CHILD_VIEW_TYPE)
            {
                convertView = mInflater.inflate(R.layout.item_buy_self_select_six, null);
                initSixSelfContentView(convertView);
            }
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
            ViewGroup.LayoutParams layoutParams = tvNum.getLayoutParams();
            layoutParams.height = mInflater.getContext()
                    .getResources()
                    .getDimensionPixelSize(R.dimen.d_circle_height1);
            layoutParams.width = mInflater.getContext()
                    .getResources()
                    .getDimensionPixelSize(R.dimen.d_circle_height1);
            if (NUMBER_RED.contains(playName))
            {
                tvNum.setBackgroundResource(R.drawable.shape_circle_red);
            }
            else if (NUMBER_GREEN.contains(playName))
            {
                tvNum.setBackgroundResource(R.drawable.shape_circle_green);
            }
            else if (NUMBER_BLUE.contains(playName))
            {
                tvNum.setBackgroundResource(R.drawable.shape_circle_blue);
            }
            else
            {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                if (playName.contains("红"))
                {
                    tvNum.setBackgroundResource(R.drawable.shape_circle_red);
                }
                else if (playName.contains("绿"))
                {
                    tvNum.setBackgroundResource(R.drawable.shape_circle_green);
                }
                else if (playName.contains("蓝"))
                {
                    tvNum.setBackgroundResource(R.drawable.shape_circle_blue);
                }
                else
                {
                    tvNum.setBackgroundResource(R.drawable.shape_circle_gray);
                }
            }
            tvNum.setLayoutParams(layoutParams);
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
        convertView.setTag(R.id.buy_view_type, CHILD_VIEW_TYPE);
        return convertView;
    }

    public void initSixSelfContentView(View convertView)
    {
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

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

}
