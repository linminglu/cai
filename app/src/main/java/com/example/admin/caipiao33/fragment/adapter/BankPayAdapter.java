package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BankPayBean;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;

/**
 * Description :
 * Author : cxy
 * Date   : 17/8/16
 */
public class BankPayAdapter extends BaseAdapter
{
    private ArrayList<BankPayBean> beanContents = new ArrayList<>();

    private LayoutInflater mInflater;

    private ListView mListView;

    private BaseActivity baseActivity;

    public BankPayAdapter(ArrayList<BankPayBean> beanContents, LayoutInflater mInflater, ListView mListView, BaseActivity baseActivity)
    {
        this.mInflater = mInflater;
        this.beanContents = beanContents;
        this.mListView = mListView;
        this.baseActivity = baseActivity;
    }

    public void setBeanContents(ArrayList<BankPayBean> beanContents)
    {
        this.beanContents = beanContents;
    }

    public ArrayList<BankPayBean> getBeanContents()
    {
        return beanContents;
    }

    @Override
    public int getCount()
    {
        return beanContents.size();
    }

    @Override
    public Object getItem(int position)
    {
        return beanContents.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final BankPayBean beanContent = beanContents.get(position);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_bankpay, parent, false);
        }
        TextView item_bankpay_bankname = ViewHolder.get(convertView, R.id.item_bankpay_bankname);
        TextView item_bankpay_addr = ViewHolder.get(convertView, R.id.item_bankpay_addr);
        TextView item_bankpay_accountname = ViewHolder.get(convertView, R.id.item_bankpay_accountname);
        CheckBox item_weixinpay_cb = ViewHolder.get(convertView, R.id.item_bankpay_cb);
        item_bankpay_bankname.setText("银行：" + beanContent.getBankName());
        item_bankpay_accountname.setText("收款人：" + beanContent.getAccountName());
        item_bankpay_addr.setText("汇款咨询：" + beanContent.getBankAddr() + "  " + beanContent.getAccountCode());
        item_weixinpay_cb.setChecked(beanContent.isSelete());
        return convertView;
    }
}
