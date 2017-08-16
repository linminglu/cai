package com.example.admin.caipiao33.fragment.adapter;

import com.example.admin.caipiao33.bean.BuyRoomBean;

import java.util.List;

/**
 * Created by shaodongPC on 2017/8/16.
 */

public class BeanGroup
{
    private List<String> groupNameList;
    private List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList;

    public List<String> getGroupNameList()
    {
        return groupNameList;
    }

    public void setGroupNameList(List<String> groupNameList)
    {
        this.groupNameList = groupNameList;
    }

    public List<List<BuyRoomBean.PlayDetailListBean.ListBean>> getChildList()
    {
        return childList;
    }

    public void setChildList(List<List<BuyRoomBean.PlayDetailListBean.ListBean>> childList)
    {
        this.childList = childList;
    }
}
