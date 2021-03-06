package com.example.admin.caipiao33.bean;

import java.util.ArrayList;

/**
 * 充值记录Bean
 */

public class ChongZhiJiLuBean
{

    /**
     * pageNo : 1
     * pageSize : 20
     * totalSize : 2
     * totalPage : 1
     * items : [{"orderNo":"1500650699868069","amount":1,"status":0,"saveTime":"2017-07-21 23:24:59","id":838}]
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
         * orderNo : 1500650699868069
         * amount : 1.0
         * status : 0
         * saveTime : 2017-07-21 23:24:59
         * id : 838
         */

        private String orderNo;
        private String amount;
        private int status;
        private String saveTime;
        private String id;

        public String getOrderNo()
        {
            return orderNo;
        }

        public void setOrderNo(String orderNo)
        {
            this.orderNo = orderNo;
        }

        public String getAmount()
        {
            return amount;
        }

        public void setAmount(String amount)
        {
            this.amount = amount;
        }

        public int getStatus()
        {
            return status;
        }

        public void setStatus(int status)
        {
            this.status = status;
        }

        public String getSaveTime()
        {
            return saveTime;
        }

        public void setSaveTime(String saveTime)
        {
            this.saveTime = saveTime;
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
