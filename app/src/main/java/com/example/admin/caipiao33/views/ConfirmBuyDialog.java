package com.example.admin.caipiao33.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.FloatUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaodongPC on 2017/8/17.
 */

public class ConfirmBuyDialog implements View.OnClickListener
{
    private final Context mContext;
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList = new ArrayList<>();
    private final ConfirmBuyListener callBack;

    MaxHeightGridView gridView;
    EditText etMoney;
    TextView tvClear;
    LinearLayout layoutInput;
    TextView tv100;
    TextView tv500;
    TextView tv1000;
    TextView tv5000;
    TextView tv10000;
    TextView tv50000;
    FlowLayout layoutQuickMoney;
    TextView tvTips;
    private final View view;

    // 本金总和
    private float totalCapital;
    // 预计中奖总和
    private float totalWinMoney;


    public ConfirmBuyDialog(Context context, List<BuyRoomBean.PlayDetailListBean.ListBean> checked, int gridNumColumns, ConfirmBuyListener callBack)
    {
        this.mContext = context;
        this.checkedList.clear();
        for (BuyRoomBean.PlayDetailListBean.ListBean bean : checked)
        {
            checkedList.add(bean.clone());
        }
        this.callBack = callBack;

        view = LayoutInflater.from(context).inflate(R.layout.item_confirm_buy, null);
        gridView = (MaxHeightGridView) view.findViewById(R.id.gridView);
        etMoney = (EditText) view.findViewById(R.id.et_money);
        tvClear = (TextView) view.findViewById(R.id.tv_clear);
        layoutInput = (LinearLayout) view.findViewById(R.id.layout_input);
        tv100 = (TextView) view.findViewById(R.id.tv_100);
        tv500 = (TextView) view.findViewById(R.id.tv_500);
        tv1000 = (TextView) view.findViewById(R.id.tv_1000);
        tv5000 = (TextView) view.findViewById(R.id.tv_5000);
        tv10000 = (TextView) view.findViewById(R.id.tv_10000);
        tv50000 = (TextView) view.findViewById(R.id.tv_50000);
        layoutQuickMoney = (FlowLayout) view.findViewById(R.id.layout_quick_money);
        tvTips = (TextView) view.findViewById(R.id.tv_tips);
        tv100.setOnClickListener(this);
        tv500.setOnClickListener(this);
        tv1000.setOnClickListener(this);
        tv5000.setOnClickListener(this);
        tv10000.setOnClickListener(this);
        tv50000.setOnClickListener(this);
        tvClear.setOnClickListener(this);

        InputFilter[] filters = {new NumberInputFilter()};
        etMoney.setFilters(filters);
        etMoney.addTextChangedListener(new TextWatcher()
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
                totalCapital = 0;
                totalWinMoney = 0;
                String s1 = s.toString();
                if (TextUtils.isEmpty(s1))
                {
                    tvTips.setVisibility(View.GONE);
                    return;
                }
                tvTips.setVisibility(View.VISIBLE);
                for (BuyRoomBean.PlayDetailListBean.ListBean bean : checkedList)
                {
                    bean.setMoney(s1);
                    int capital = Integer.valueOf(bean.getMoney());
                    float bonus = Float.valueOf(bean.getBonus());
                    totalCapital += capital;
                    totalWinMoney += capital * bonus;
                }
                tvTips.setText(Html.fromHtml(mContext.getString(R.string.s_buy_explain, checkedList.size(), String
                        .valueOf(FloatUtils.format(totalCapital)), String.valueOf(FloatUtils.format(totalWinMoney)), String
                        .valueOf(FloatUtils.format(totalWinMoney - totalCapital)))));
            }
        });

        setGridNumColumns(gridNumColumns);

        updateUI(checked);
    }

    private String updateMoney(int num)
    {
        try
        {
            int afterNum = Integer.valueOf(etMoney.getText().toString());
            return afterNum + num + "";
        }
        catch (Exception e)
        {

        }
        return num + "";
    }

    public void show()
    {
        new MaterialDialog.Builder(mContext).title("确认下注")
                .customView(view, false)
                .positiveText("下注")
                .positiveColor(mContext.getResources().getColor(R.color.blue))
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback()
                {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {
                        if (totalCapital <= 0)
                        {
                            ToastUtil.show("请输入买入金额");
                            return;
                        }
                        callBack.onConfirmBuyListener(checkedList);
                    }
                })
                .show();
    }

    public void setGridNumColumns(int gridNumColumns)
    {
        if (null != gridView)
        {
            gridView.setNumColumns(gridNumColumns);
        }
    }

    public void updateUI(List<BuyRoomBean.PlayDetailListBean.ListBean> checked)
    {
        if (null == checked || checked.size() == 0)
        {
            return;
        }
        etMoney.setText("");
        this.checkedList.clear();
        for (BuyRoomBean.PlayDetailListBean.ListBean bean : checked)
        {
            checkedList.add(bean.clone());
        }

        gridView.setAdapter(new MyAdapter());

        BuyRoomBean.PlayDetailListBean.ListBean listBean = checked.get(0);
        if (TextUtils.isEmpty(listBean.getMoney()))
        {
            // 快捷下注
            layoutQuickMoney.setVisibility(View.VISIBLE);
            layoutInput.setVisibility(View.VISIBLE);
            tvTips.setVisibility(View.GONE);
        }
        else
        {
            // 自选下注
            layoutQuickMoney.setVisibility(View.GONE);
            layoutInput.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
            for (BuyRoomBean.PlayDetailListBean.ListBean bean : checkedList)
            {
                int capital = Integer.valueOf(bean.getMoney());
                float bonus = Float.valueOf(bean.getBonus());
                totalCapital += capital;
                totalWinMoney += capital * bonus;
            }
            tvTips.setText(Html.fromHtml(mContext.getString(R.string.s_buy_explain, checkedList.size(), String
                    .valueOf(FloatUtils.format(totalCapital)), String.valueOf(FloatUtils.format(totalWinMoney)), String
                    .valueOf(FloatUtils.format(totalWinMoney - totalCapital)))));
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_100:
                etMoney.setText(updateMoney(100));
                etMoney.setSelection(etMoney.length());
                break;
            case R.id.tv_500:
                etMoney.setText(updateMoney(500));
                etMoney.setSelection(etMoney.length());
                break;
            case R.id.tv_1000:
                etMoney.setText(updateMoney(1000));
                etMoney.setSelection(etMoney.length());
                break;
            case R.id.tv_5000:
                etMoney.setText(updateMoney(5000));
                etMoney.setSelection(etMoney.length());
                break;
            case R.id.tv_10000:
                etMoney.setText(updateMoney(10000));
                etMoney.setSelection(etMoney.length());
                break;
            case R.id.tv_50000:
                etMoney.setText(updateMoney(50000));
                etMoney.setSelection(etMoney.length());
                break;
            case R.id.tv_clear:
                etMoney.setText("");
                etMoney.setSelection(etMoney.length());
                break;
            default:
                break;
        }
    }

    public interface ConfirmBuyListener
    {
        void onConfirmBuyListener(List<BuyRoomBean.PlayDetailListBean.ListBean> checked);
    }

    public class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return checkedList.size();
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
            if (null == convertView)
            {
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.item_confirm_buy_item, null);
            }
            TextView tvContent = ViewHolder.get(convertView, R.id.tv_content);
            BuyRoomBean.PlayDetailListBean.ListBean listBean = checkedList.get(position);
            tvContent.setText(listBean.getParentName() + "\n" + listBean.getPlayName());
            return convertView;
        }
    }
}
