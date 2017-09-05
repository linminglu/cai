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
import com.example.admin.caipiao33.bean.AliPayBean;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;

/**
 * Description :
 * Author : cxy
 * Date   : 17/8/16
 */
public class AliPayAdapter extends BaseAdapter
{
    private ArrayList<AliPayBean> beanContents = new ArrayList<>();

    private LayoutInflater mInflater;

    private ListView mListView;

    private BaseActivity baseActivity;

    public AliPayAdapter(ArrayList<AliPayBean> beanContents, LayoutInflater mInflater, ListView mListView, BaseActivity baseActivity)
    {
        this.mInflater = mInflater;
        this.beanContents = beanContents;
        this.mListView = mListView;
        this.baseActivity = baseActivity;
    }

    public void setBeanContents(ArrayList<AliPayBean> beanContents)
    {
        this.beanContents = beanContents;
    }

    public ArrayList<AliPayBean> getBeanContents()
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
        final AliPayBean beanContent = beanContents.get(position);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_weixinpay, parent, false);
        }
        TextView item_weixinpay_name = ViewHolder.get(convertView, R.id.item_weixinpay_name);
        CheckBox item_weixinpay_cb = ViewHolder.get(convertView, R.id.item_weixinpay_cb);
        TextView item_weixinpay_sub = ViewHolder.get(convertView, R.id.item_weixinpay_sub);
        item_weixinpay_name.setText(beanContent.getPayName());
        item_weixinpay_sub.setText(beanContent.getPayDesc());
        item_weixinpay_cb.setChecked(beanContent.isSelete());
        //        item_weixinpay_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        //        {
        //            @Override
        //            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        //            {
        //                for (int i = 0; i < beanContents.size(); i++)
        //                {
        //                    beanContents.get(i).setSelete(false);
        //                }
        //                beanContent.setSelete(isChecked);
        //            }
        //        });
        return convertView;
    }
}
