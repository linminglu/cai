package com.example.admin.caipiao33.bean;

import java.util.ArrayList;

/**
 * 签到记录Bean
 */

public class QianDaoJiLuBean
{

    /**
     * pageNo : 1
     * pageSize : 20
     * totalSize : 2
     * totalPage : 1
     * items : [{"checkinDay":"20170722","recharge":"200.00","checkinTime":"2017-07-22 13:48:20","giftAmount":0}]
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
         * checkinDay : 20170722
         * recharge : 200.00
         * checkinTime : 2017-07-22 13:48:20
         * giftAmount : 0.0
         */

        private String checkinDay;
        private String recharge;
        private String checkinTime;
        private String giftAmount;

        public String getCheckinDay()
        {
            return checkinDay;
        }

        public void setCheckinDay(String checkinDay)
        {
            this.checkinDay = checkinDay;
        }

        public String getRecharge()
        {
            return recharge;
        }

        public void setRecharge(String recharge)
        {
            this.recharge = recharge;
        }

        public String getCheckinTime()
        {
            return checkinTime;
        }

        public void setCheckinTime(String checkinTime)
        {
            this.checkinTime = checkinTime;
        }

        public String getGiftAmount()
        {
            return giftAmount;
        }

        public void setGiftAmount(String giftAmount)
        {
            this.giftAmount = giftAmount;
        }
    }
}
