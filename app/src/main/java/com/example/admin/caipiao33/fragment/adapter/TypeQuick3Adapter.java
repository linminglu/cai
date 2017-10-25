package com.example.admin.caipiao33.fragment.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.QuickBuyFragment;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 6合自选不中的适配器
 */

public class TypeQuick3Adapter extends TypeBeforeAdapter
{
    private static int COUNT = 4;
    /**
     * 11 - 安徽快三
     * 10 - 江苏快三
     * 17 - 广西快三
     */
    public static final List<String> QUICK3List = Arrays.asList("11", "10", "17");
    private final Drawable drawable1;
    private final Drawable drawable2;
    private final Drawable drawable3;
    private final Drawable drawable4;
    private final Drawable drawable5;
    private final Drawable drawable6;

    public TypeQuick3Adapter(LayoutInflater inflater, BuyRoomBean bean, int type)
    {
        this.mInflater = inflater;
        this.mBuyRoomBean = bean;
        this.mType = type;
        updateData(bean);
        drawable1 = mInflater.getContext().getResources().getDrawable(R.mipmap.touzi_01);
        drawable1.setBounds(0, 0, drawable1.getIntrinsicWidth(), drawable1.getIntrinsicHeight());
        drawable2 = mInflater.getContext().getResources().getDrawable(R.mipmap.touzi_02);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        drawable3 = mInflater.getContext().getResources().getDrawable(R.mipmap.touzi_03);
        drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        drawable4 = mInflater.getContext().getResources().getDrawable(R.mipmap.touzi_04);
        drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        drawable5 = mInflater.getContext().getResources().getDrawable(R.mipmap.touzi_05);
        drawable5.setBounds(0, 0, drawable5.getIntrinsicWidth(), drawable5.getIntrinsicHeight());
        drawable6 = mInflater.getContext().getResources().getDrawable(R.mipmap.touzi_06);
        drawable6.setBounds(0, 0, drawable6.getIntrinsicWidth(), drawable6.getIntrinsicHeight());
    }

    /**
     * 利用bonus来区分是快捷还是自选
     * @param playName
     * @param bonus
     */
    private SpannableString getQuick3NameString(String playName, String bonus) {
        SpannableString msp;
        if (StringUtils.isEmpty(bonus)) {
            // 快捷
            msp = new SpannableString(playName);
        } else {
            // 自选
            int start;
            if (COUNT == 3) {
                msp = new SpannableString(playName + "\n" + bonus);
                start = playName.length() + 1;
            } else {
                msp = new SpannableString(playName + " " + bonus);
                start = playName.length() + 1;
            }
            msp.setSpan(new ForegroundColorSpan(Color.parseColor("#C30D23")), start, start + bonus
                    .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        switch (playName)
        {
            case "1":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "2":
                msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "3":
                msp.setSpan(new ImageSpan(drawable3), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "4":
                msp.setSpan(new ImageSpan(drawable4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "5":
                msp.setSpan(new ImageSpan(drawable5), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "6":
                msp.setSpan(new ImageSpan(drawable6), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "111":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable1), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable1), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "222":
                msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable2), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable2), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "333":
                msp.setSpan(new ImageSpan(drawable3), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable3), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable3), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "444":
                msp.setSpan(new ImageSpan(drawable4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable4), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable4), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "555":
                msp.setSpan(new ImageSpan(drawable5), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable5), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable5), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "666":
                msp.setSpan(new ImageSpan(drawable6), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 2, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "12":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable2), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "13":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable3), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "14":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable4), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "15":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable5), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "16":
                msp.setSpan(new ImageSpan(drawable1), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "23":
                msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable3), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "24":
                msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable4), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "25":
                msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable5), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "26":
                msp.setSpan(new ImageSpan(drawable2), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "34":
                msp.setSpan(new ImageSpan(drawable3), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable4), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "35":
                msp.setSpan(new ImageSpan(drawable3), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable5), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "36":
                msp.setSpan(new ImageSpan(drawable3), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "45":
                msp.setSpan(new ImageSpan(drawable4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable5), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "46":
                msp.setSpan(new ImageSpan(drawable4), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            case "56":
                msp.setSpan(new ImageSpan(drawable5), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                msp.setSpan(new ImageSpan(drawable6), 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                break;
            default:
                break;
        }
        return msp;
    }


    /**
     * 重置数据
     *
     * @param bean
     */
    public void updateData(BuyRoomBean bean)
    {
        this.mBuyRoomBean = bean;
        String playName = mBuyRoomBean.getPlayName();
        if ("围骰、全骰".equals(playName) || "长牌".equals(playName) || "短牌".equals(playName)) {
            COUNT = 3;
        }
        creatDataNormal();
    }

    private void creatDataNormal()
    {
        String playName = mBuyRoomBean.getPlayName();
        boolean isNumberType = false;
        if ("点数".equals(playName))
        {
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
                    e.setPlayName(e.getPlayName() + (isNumberType ? "点" : ""));
                    itemList.add(e);
                }
                childList.add(itemList);
            }
            beanGroup.setChildList(childList);
            mDataList.add(beanGroup);
        }
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (null == convertView)
        {
            convertView = mInflater.inflate(R.layout.item_buy_quick_group, null);
        }

        TextView tv1 = ViewHolder.get(convertView, R.id.tv_1);
        TextView tv2 = ViewHolder.get(convertView, R.id.tv_2);
        TextView tv3 = ViewHolder.get(convertView, R.id.tv_3);
        TextView tv4 = ViewHolder.get(convertView, R.id.tv_4);
        ImageView ivArrow = ViewHolder.get(convertView, R.id.iv_arrow);

        if (COUNT == 3) {
            tv4.setVisibility(View.GONE);
        }

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

            if (COUNT == 3) {
                layout4.setVisibility(View.GONE);
            }

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
                String playName = listBean.getPlayName();
                SpannableString msp = getQuick3NameString(playName, null);// 快捷这里bonus要是null
                tvName.setText(msp);
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
        else
        {
            // 自选下注的类型
            if (null == convertView)
            {
                convertView = mInflater.inflate(R.layout.item_buy_self_select_quick3, null);
                initSelfContentView(convertView);
            }
            View layout1 = ViewHolder.get(convertView, R.id.layout1);
            View layout2 = ViewHolder.get(convertView, R.id.layout2);
            View layout3 = ViewHolder.get(convertView, R.id.layout3);
            View layout4 = ViewHolder.get(convertView, R.id.layout4);
            layout1.setVisibility(View.INVISIBLE);
            layout2.setVisibility(View.INVISIBLE);
            layout3.setVisibility(View.INVISIBLE);
            layout4.setVisibility(View.INVISIBLE);

            if (COUNT == 3) {
                layout4.setVisibility(View.GONE);
            }

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

                String playName = listBean.getPlayName();
                SpannableString msp = getQuick3NameString(playName, listBean.getBonus());// 快捷这里bonus要是null
                tvName.setText(msp);
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
    }
}
