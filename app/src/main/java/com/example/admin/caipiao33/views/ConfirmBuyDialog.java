package com.example.admin.caipiao33.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by shaodongPC on 2017/8/17.
 */

public class ConfirmBuyDialog implements View.OnClickListener
{
    private final Context mContext;
    private List<BuyRoomBean.PlayDetailListBean.ListBean> checkedList;
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

    public ConfirmBuyDialog(Context context, List<BuyRoomBean.PlayDetailListBean.ListBean> checked, ConfirmBuyListener callBack)
    {
        this.mContext = context;
        this.checkedList = checked;
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
                String s1 = s.toString();
                if (TextUtils.isEmpty(s1)) {

                }
            }
        });

        updateUI(checked);
    }

    public void show() {
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

                    }
                })
                .show();
    }

    private void updateUI(List<BuyRoomBean.PlayDetailListBean.ListBean> checked)
    {
        if (null == checked || checked.size() == 0) {
            return;
        }
        this.checkedList = checked;

        gridView.setAdapter(new MyAdapter());

        BuyRoomBean.PlayDetailListBean.ListBean listBean = checked.get(0);
        if (TextUtils.isEmpty(listBean.getMoney())) {
            // 快捷下注
            layoutQuickMoney.setVisibility(View.VISIBLE);
            layoutInput.setVisibility(View.VISIBLE);
        } else {
            // 自选下注
            layoutQuickMoney.setVisibility(View.VISIBLE);
            layoutInput.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.tv_100:
                etMoney.setText("100");
                break;
            case R.id.tv_500:
                etMoney.setText("500");
                break;
            case R.id.tv_1000:
                etMoney.setText("1000");
                break;
            case R.id.tv_5000:
                etMoney.setText("5000");
                break;
            case R.id.tv_10000:
                etMoney.setText("10000");
                break;
            case R.id.tv_50000:
                etMoney.setText("50000");
                break;
            case R.id.tv_clear:
                etMoney.setText("");
                break;
            default:
                break;
        }
    }

    public interface ConfirmBuyListener {
        void onConfirmBuyListener(List<BuyRoomBean.PlayDetailListBean.ListBean> checked);
    }

    public class MyAdapter extends BaseAdapter {

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
            if (null == convertView) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_confirm_buy_item, null);
            }
            TextView tvContent = ViewHolder.get(convertView, R.id.tv_content);
            BuyRoomBean.PlayDetailListBean.ListBean listBean = checkedList.get(position);
            tvContent.setText(listBean.getPlayName());
            return convertView;
        }
    }
}
