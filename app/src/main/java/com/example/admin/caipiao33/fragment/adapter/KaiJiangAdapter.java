package com.example.admin.caipiao33.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.caipiao33.BaseActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.KaiJiangDTBean;
import com.example.admin.caipiao33.utils.ViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description :
 * Author : cxy
 * Date   : 17/8/16
 */
public class KaiJiangAdapter extends BaseAdapter
{
    private ArrayList<KaiJiangDTBean> beanContents = new ArrayList<>();

    private LayoutInflater mInflater;

    private ListView mListView;

    private BaseActivity baseActivity;

    public KaiJiangAdapter(ArrayList<KaiJiangDTBean> beanContents, LayoutInflater mInflater, ListView mListView, BaseActivity baseActivity)
    {
        this.mInflater = mInflater;
        this.beanContents = beanContents;
        this.mListView = mListView;
        this.baseActivity = baseActivity;
    }

    public void setBeanContents(ArrayList<KaiJiangDTBean> beanContents)
    {
        this.beanContents = beanContents;
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
        KaiJiangDTBean beanContent = beanContents.get(position);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.item_kaijiang, null);
        }
        TextView itemKaijiangName = ViewHolder.get(convertView, R.id.item_kaijiang_name);
        TextView itemKaijiangQishu = ViewHolder.get(convertView, R.id.item_kaijiang_qishu);
        TextView itemKaijiangHaoma = ViewHolder.get(convertView, R.id.item_kaijiang_haoma);
        itemKaijiangName.setText(beanContent.get_$Name165());
        itemKaijiangQishu.setText(beanContent.getOpenTime());
        itemKaijiangHaoma.setText(beanContent.getContent());
        return convertView;
    }
}
