package com.example.admin.caipiao33.fragment.adapter;

import android.widget.BaseExpandableListAdapter;

import com.example.admin.caipiao33.bean.BuyRoomBean;

import java.util.List;

/**
 * Created by shaodongPC on 2017/8/16.
 */

public abstract class MyBaseBuyAdapter extends BaseExpandableListAdapter
{
    public abstract void clearChecked();
    public abstract List<BuyRoomBean.PlayDetailListBean.ListBean> getCheckedList();
}
