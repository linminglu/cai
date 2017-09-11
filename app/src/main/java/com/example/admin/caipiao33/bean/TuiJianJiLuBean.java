package com.example.admin.caipiao33.bean;

import java.util.ArrayList;

/**
 * 签到记录Bean
 */

public class TuiJianJiLuBean
{

    /**
     * pageNo : 1
     * pageSize : 20
     * totalSize : 1
     * totalPage : 1
     * items : [{"yearmonth":201707,"countday":1,"totalBet":100,"amount":100,"addTime":"2017-05-30 07:00:00"}]
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
         * yearmonth : 201707
         * countday : 1
         * totalBet : 100.0
         * amount : 100.0
         * addTime : 2017-05-30 07:00:00
         */

        private String yearmonth;
        private String countday;
        private String totalBet;
        private String amount;
        private String addTime;

        public String getYearmonth()
        {
            return yearmonth;
        }

        public void setYearmonth(String yearmonth)
        {
            this.yearmonth = yearmonth;
        }

        public String getCountday()
        {
            return countday;
        }

        public void setCountday(String countday)
        {
            this.countday = countday;
        }

        public String getTotalBet()
        {
            return totalBet;
        }

        public void setTotalBet(String totalBet)
        {
            this.totalBet = totalBet;
        }

        public String getAmount()
        {
            return amount;
        }

        public void setAmount(String amount)
        {
            this.amount = amount;
        }

        public String getAddTime()
        {
            return addTime;
        }

        public void setAddTime(String addTime)
        {
            this.addTime = addTime;
        }
    }
}
