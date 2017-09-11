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
import com.example.admin.caipiao33.bean.OnLinePayBean;
import com.example.admin.caipiao33.bean.WeiXinPayBean;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;

import static com.example.admin.caipiao33.R.id.item_weixinpay_cb;

/**
 * Description :
 * Author : cxy
 * Date   : 17/8/16
 */
public class OnLinePayAdapter extends BaseAdapter
{
    private ArrayList<OnLinePayBean.ExpandBean.BankListBean> beanContents = new ArrayList<>();

    private LayoutInflater mInflater;

    private ListView mListView;

    private BaseActivity baseActivity;

    public OnLinePayAdapter(ArrayList<OnLinePayBean.ExpandBean.BankListBean> beanContents, LayoutInflater mInflater, ListView mListView, BaseActivity baseActivity)
    {
        this.mInflater = mInflater;
        this.beanContents = beanContents;
        this.mListView = mListView;
        this.baseActivity = baseActivity;
    }

    public void setBeanContents(ArrayList<OnLinePayBean.ExpandBean.BankListBean> beanContents)
    {
        this.beanContents = beanContents;
    }

    public ArrayList<OnLinePayBean.ExpandBean.BankListBean> getBeanContents()
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
        final OnLinePayBean.ExpandBean.BankListBean beanContent = beanContents.get(position);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_onlinepay, parent, false);
        }
        TextView item_onlinepay_name = ViewHolder.get(convertView, R.id.item_onlinepay_name);
        CheckBox item_onlinepay_cb = ViewHolder.get(convertView, R.id.item_onlinepay_cb);
        item_onlinepay_name.setText(beanContent.getName());
        item_onlinepay_cb.setChecked(beanContent.isSelete());
        return convertView;
    }
}
