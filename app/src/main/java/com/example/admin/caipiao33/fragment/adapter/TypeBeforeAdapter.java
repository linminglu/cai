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
import android.widget.EditText;
import android.widget.ImageView;
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

public class TypeBeforeAdapter extends MyBaseBuyAdapter implements View.OnClickListener
{
    /*
    分类：
        1. 六合
        2. 前四个四个折叠
        3. 第一个不折叠，后四个四个折叠
     */

    /**
     * 52 - 三分PK10
     * 9 - 北京赛车(PK10)
     * 34 - 幸运飞艇
     */
    private final List<String> beforeList = Arrays.asList("52", "9", "34");

    /**
     * 27 - 广东快乐十分
     * 28 - 重庆幸运农场
     */
    private final List<String> afterList = Arrays.asList("27", "28");


    private static final int COUNT = 4;
    protected BuyRoomBean mBuyRoomBean;
    protected LayoutInflater mInflater;
    protected int mType;
    protected List<BuyRoomBean.PlayDetailListBean.ListBean> mCheckedList = new ArrayList<>();
    protected List<BeanGroup> mDataList;
    // 用来区分是当前这种类型的view，子类中TypeSixAdapter可能会调用父类的getGroupView，这时这个contentView不能复用
    private final int GROUP_VIEW_TYPE = 1001;
    // 用来区分是当前这种类型的view，子类中TypeSixAdapter可能会调用父类的getChildView，这时这个contentView不能复用
    private final int CHILD_VIEW_TYPE = 1002;

    public TypeBeforeAdapter()
    {

    }

    public TypeBeforeAdapter(LayoutInflater inflater, BuyRoomBean bean, int type)
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
        String num = bean.getNum();
        if (beforeList.contains(num))
        {
            creatDataBefore();
        }
        else if (afterList.contains(num))
        {
            String playName = bean.getPlayName();
            if ("数字盘".equals(playName)) {
                creatDataNormal();
            } else if (playName.startsWith("第") && playName.endsWith("球")) {
                // 数据处理
                List<BuyRoomBean.PlayDetailListBean> playDetailList = bean.getPlayDetailList();
                BuyRoomBean.PlayDetailListBean result = new BuyRoomBean.PlayDetailListBean();
                result.setName(playName);
                for (BuyRoomBean.PlayDetailListBean playDetailListBean : playDetailList) {
                    String name = playDetailListBean.getName();
                    if (playName.equals(name)) {
                        List<BuyRoomBean.PlayDetailListBean.ListBean> list = result.getList();
                        if (null == list) {
                            list = new ArrayList<>();
                            result.setList(list);
                        }
                        List<BuyRoomBean.PlayDetailListBean.ListBean> list1 = playDetailListBean
                                .getList();
                        if (list1.size() >= 20) {
                            list.addAll(0, list1);
                        } else {
                            list.addAll(list1);
                        }
                    }
                }
                mBuyRoomBean.getPlayDetailList().clear();
                mBuyRoomBean.getPlayDetailList().add(result);
                creatDataNormal();
            } else {
                creatDataAfter();
            }
        }
        else
        {
            creatDataNormal();
        }
    }

    private void creatDataNormal()
    {
        String playName = mBuyRoomBean.getPlayName();
        boolean isNumberType = false;
        if ("数字盘".equals(playName)) {
            isNumberType = true;
        }
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
                    e.setPlayName(e.getPlayName() + (isNumberType ? "号" : ""));
                    itemList.add(e);
                }
                childList.add(itemList);
            }
            beanGroup.setChildList(childList);
            mDataList.add(beanGroup);
        }
    }

    /**
     * 第一个不合并，后面数据四个四个合并
     */
    private void creatDataAfter()
    {
        String playName = mBuyRoomBean.getPlayName();
        boolean isNumberType = false;
        if ("数字盘".equals(playName)) {
            isNumberType = true;
        }
        int totalSize = mBuyRoomBean.getPlayDetailList().size();
        int groupSize = 1 + (totalSize - 1) / COUNT + (totalSize - 1) % COUNT;
        mDataList = new ArrayList<>(groupSize);
        for (int i = 0; i < groupSize; i++)
        {
            BeanGroup beanGroup = new BeanGroup();
            if (i > 0 && i <= (totalSize - 1) / COUNT)
            {
                List<String> groupNameList = new ArrayList<>(COUNT);
                int childListSize = 0;
                for (int j = 0; j < COUNT; j++)
                {
                    BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                            .get(1 + COUNT * (i - 1) + j);
                    List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = playDetailListBean.getList();
                    if (childListSize < itemList.size())
                    {
                        childListSize = itemList.size();
                    }
                    groupNameList.add(playDetailListBean.getName());
                }
                beanGroup.setGroupNameList(groupNameList);

                List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);
                for (int j = 0; j < childListSize; j++)
                {
                    List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT);
                    for (int k = 0; k < COUNT; k++)
                    {
                        BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                                .get(1 + COUNT * (i - 1) + k);
                        List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
                        if (j < list.size())
                        {
                            BuyRoomBean.PlayDetailListBean.ListBean e = list.get(j);
                            e.setParentName(playDetailListBean.getName());
                            e.setPlayName(e.getPlayName() + (isNumberType ? "号" : ""));
                            itemList.add(e);
                        }
                    }
                    childList.add(itemList);
                }
                beanGroup.setChildList(childList);
            }
            else
            {
                BuyRoomBean.PlayDetailListBean playDetailListBean;
                if (i == 0)
                {
                    playDetailListBean = mBuyRoomBean.getPlayDetailList().get(i);
                }
                else
                {
                    playDetailListBean = mBuyRoomBean.getPlayDetailList()
                            .get(1 + ((totalSize - 1) / COUNT) * COUNT + (i - (totalSize - 1) / COUNT) - 1);
                }
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
                        e.setPlayName(e.getPlayName() + (isNumberType ? "号" : ""));
                        itemList.add(e);
                    }
                    childList.add(itemList);
                }
                beanGroup.setChildList(childList);
            }
            mDataList.add(beanGroup);
        }
    }

    private void creatDataBefore()
    {
        String playName = mBuyRoomBean.getPlayName();
        boolean isNumberType = false;
        if ("数字盘".equals(playName)) {
            isNumberType = true;
        }
        int totalSize = mBuyRoomBean.getPlayDetailList().size();
        int groupSize = totalSize / COUNT + totalSize % COUNT;
        mDataList = new ArrayList<>(groupSize);
        for (int i = 0; i < groupSize; i++)
        {
            BeanGroup beanGroup = new BeanGroup();
            if (i < totalSize / COUNT)
            {
                List<String> groupNameList = new ArrayList<>(COUNT);
                int childListSize = 0;
                for (int j = 0; j < COUNT; j++)
                {
                    BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                            .get(COUNT * i + j);
                    List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = playDetailListBean.getList();
                    if (childListSize < itemList.size())
                    {
                        childListSize = itemList.size();
                    }
                    groupNameList.add(playDetailListBean.getName());
                }
                beanGroup.setGroupNameList(groupNameList);

                List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList = new ArrayList<>(childListSize);
                for (int j = 0; j < childListSize; j++)
                {
                    List<BuyRoomBean.PlayDetailListBean.ListBean> itemList = new ArrayList<>(COUNT);
                    for (int k = 0; k < COUNT; k++)
                    {
                        BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                                .get(COUNT * i + k);
                        List<BuyRoomBean.PlayDetailListBean.ListBean> list = playDetailListBean.getList();
                        if (j < list.size())
                        {
                            BuyRoomBean.PlayDetailListBean.ListBean e = list.get(j);
                            e.setParentName(playDetailListBean.getName());
                            e.setPlayName(e.getPlayName() + (isNumberType ? "号" : ""));
                            itemList.add(e);
                        }
                    }
                    childList.add(itemList);
                }
                beanGroup.setChildList(childList);
            }
            else
            {
                BuyRoomBean.PlayDetailListBean playDetailListBean = mBuyRoomBean.getPlayDetailList()
                        .get((totalSize / COUNT) * COUNT + (i - totalSize / COUNT));
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
                        e.setPlayName(e.getPlayName() + (isNumberType ? "号" : ""));
                        itemList.add(e);
                    }
                    childList.add(itemList);
                }
                beanGroup.setChildList(childList);
            }
            mDataList.add(beanGroup);
        }
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
    }

    /**
     * 获取已选中的列表
     *
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
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group, null);
        }
        else
        {
            // 不能复用的情况
            Object tag = convertView.getTag(R.id.buy_view_type);
            if (null == tag || (int) tag != GROUP_VIEW_TYPE)
            {
                convertView = mInflater.inflate(R.layout.item_buy_quick_group, null);
            }
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
                case 3:
                    tv4.setText(groupNameList.get(i));
                    break;
                default:
                    break;
            }
        }
        convertView.setTag(R.id.buy_view_type, GROUP_VIEW_TYPE);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (mType == QuickBuyFragment.TYPE_QUICK)
        {
            // 快捷下注的类型
            if (null == convertView)
            {
                convertView = mInflater.inflate(R.layout.item_buy_quick, null);
                ViewHolder.get(convertView, R.id.layout1).setOnClickListener(this);
                ViewHolder.get(convertView, R.id.layout2).setOnClickListener(this);
                ViewHolder.get(convertView, R.id.layout3).setOnClickListener(this);
                ViewHolder.get(convertView, R.id.layout4).setOnClickListener(this);
            }
            else
            {
                // 不能复用的情况
                Object tag = convertView.getTag(R.id.buy_view_type);
                if (null == tag || (int) tag != CHILD_VIEW_TYPE)
                {
                    convertView = mInflater.inflate(R.layout.item_buy_quick, null);
                    ViewHolder.get(convertView, R.id.layout1).setOnClickListener(this);
                    ViewHolder.get(convertView, R.id.layout2).setOnClickListener(this);
                    ViewHolder.get(convertView, R.id.layout3).setOnClickListener(this);
                    ViewHolder.get(convertView, R.id.layout4).setOnClickListener(this);
                }
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
            View layout4 = ViewHolder.get(convertView, R.id.layout4);
            TextView tvName4 = ViewHolder.get(convertView, R.id.tv_name4);
            TextView tvOdds4 = ViewHolder.get(convertView, R.id.tv_odds4);

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
                    case 3:
                        layout = layout4;
                        tvName = tvName4;
                        tvOdds = tvOdds4;
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
            convertView.setTag(R.id.buy_view_type, CHILD_VIEW_TYPE);
            return convertView;
        }
        else
        {
            // 自选下注的类型
            if (null == convertView)
            {
                convertView = mInflater.inflate(R.layout.item_buy_self_select, null);
                initSelfContentView(convertView);
            }
            else
            {
                // 不能复用的情况
                Object tag = convertView.getTag(R.id.buy_view_type);
                if (null == tag || (int) tag != CHILD_VIEW_TYPE)
                {
                    convertView = mInflater.inflate(R.layout.item_buy_self_select, null);
                    initSelfContentView(convertView);
                }
            }
            View layout1 = ViewHolder.get(convertView, R.id.layout1);
            View layout2 = ViewHolder.get(convertView, R.id.layout2);
            View layout3 = ViewHolder.get(convertView, R.id.layout3);
            View layout4 = ViewHolder.get(convertView, R.id.layout4);
            layout1.setVisibility(View.INVISIBLE);
            layout2.setVisibility(View.INVISIBLE);
            layout3.setVisibility(View.INVISIBLE);
            layout4.setVisibility(View.INVISIBLE);

            TextView tvName1 = ViewHolder.get(convertView, R.id.tv_name1);
            TextView tvName2 = ViewHolder.get(convertView, R.id.tv_name2);
            TextView tvName3 = ViewHolder.get(convertView, R.id.tv_name3);
            TextView tvName4 = ViewHolder.get(convertView, R.id.tv_name4);
            EditText etNum1 = ViewHolder.get(convertView, R.id.et_num1);
            etNum1.setTag(R.id.buy_data, null);
            EditText etNum2 = ViewHolder.get(convertView, R.id.et_num2);
            etNum2.setTag(R.id.buy_data, null);
            EditText etNum3 = ViewHolder.get(convertView, R.id.et_num3);
            etNum3.setTag(R.id.buy_data, null);
            EditText etNum4 = ViewHolder.get(convertView, R.id.et_num4);
            etNum4.setTag(R.id.buy_data, null);

            List<BuyRoomBean.PlayDetailListBean.ListBean> listBeen = mDataList.get(groupPosition)
                    .getChildList()
                    .get(childPosition);
            for (int i = 0; i < listBeen.size(); i++)
            {
                BuyRoomBean.PlayDetailListBean.ListBean listBean = listBeen.get(i);
                View layout = null;
                TextView tvName = null;
                EditText etNum = null;
                switch (i)
                {
                    case 0:
                        layout = layout1;
                        tvName = tvName1;
                        etNum = etNum1;
                        break;
                    case 1:
                        layout = layout2;
                        tvName = tvName2;
                        etNum = etNum2;
                        break;
                    case 2:
                        layout = layout3;
                        tvName = tvName3;
                        etNum = etNum3;
                        break;
                    case 3:
                        layout = layout4;
                        tvName = tvName4;
                        etNum = etNum4;
                        break;
                    default:
                        break;
                }
                layout.setVisibility(View.VISIBLE);
                SpannableString ss = new SpannableString(listBean.getPlayName() + " " + listBean.getBonus());
                int start = listBean.getPlayName().length() + 1;
                ss.setSpan(new ForegroundColorSpan(Color.parseColor("#C30D23")), start, start + listBean
                        .getBonus()
                        .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvName.setText(ss);
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
    }

    /**
     * 初始化自选下注类型的contentView
     *
     * @param convertView
     */
    private void initSelfContentView(View convertView)
    {
        InputFilter[] filters = {new NumberInputFilter()};
        final EditText etNum1 = ViewHolder.get(convertView, R.id.et_num1);
        etNum1.setFilters(filters);
        etNum1.addTextChangedListener(new TextWatcher()
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
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) etNum1
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
        final EditText etNum2 = ViewHolder.get(convertView, R.id.et_num2);
        etNum2.setFilters(filters);
        etNum2.addTextChangedListener(new TextWatcher()
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
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) etNum2
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
        final EditText etNum3 = ViewHolder.get(convertView, R.id.et_num3);
        etNum3.setFilters(filters);
        etNum3.addTextChangedListener(new TextWatcher()
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
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) etNum3
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
        final EditText etNum4 = ViewHolder.get(convertView, R.id.et_num4);
        etNum4.setFilters(filters);
        etNum4.addTextChangedListener(new TextWatcher()
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
                BuyRoomBean.PlayDetailListBean.ListBean listBean = (BuyRoomBean.PlayDetailListBean.ListBean) etNum4
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
                }
                else
                {
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
}
