package com.example.admin.caipiao33.bean;

import java.util.ArrayList;

/**
 * 签到记录Bean
 */

public class GeRenXiaoXiBean
{

    /**
     * pageNo : 1
     * pageSize : 20
     * totalSize : 1
     * totalPage : 1
     * items : [{"title":"重要消息(必看)","status":1,"addTime":"2017-07-22 10:15:21","id":130}]
     */

    private int pageNo;
    private int pageSize;
    private int totalSize;
    private int totalPage;
    private ArrayList<ItemsBean> items;

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getTotalSize()
    {
        return totalSize;
    }

    public void setTotalSize(int totalSize)
    {
        this.totalSize = totalSize;
    }

    public int getTotalPage()
    {
        return totalPage;
    }

    public void setTotalPage(int totalPage)
    {
        this.totalPage = totalPage;
    }

    public ArrayList<ItemsBean> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<ItemsBean> items)
    {
        this.items = items;
    }

    public static class ItemsBean
    {
        /**
         * title : 重要消息(必看)
         * status : 1
         * addTime : 2017-07-22 10:15:21
         * id : 130
         */

        private String title;
        private int status;
        private String addTime;
        private String id;

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }

        public String getAddTime()
        {
            return addTime;
        }

        public void setAddTime(String addTime)
        {
            this.addTime = addTime;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }
    }
}
