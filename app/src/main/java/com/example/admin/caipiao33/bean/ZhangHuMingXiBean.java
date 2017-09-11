package com.example.admin.caipiao33.bean;

import java.util.ArrayList;

/**
 * 账户明细Bean
 */

public class ZhangHuMingXiBean
{

    /**
     * pageNo : 1
     * pageSize : 20
     * totalSize : 7640
     * totalPage : 382
     * items : [{"orderNo":"14970055665136422","inOut":1,"amount":99,"amountAfter":1.523109228E8,"expand":{"remark":"派奖"}}]
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
         * orderNo : 14970055665136422
         * inOut : 1
         * amount : 99.0
         * amountAfter : 1.523109228E8
         * expand : {"remark":"派奖"}
         */

        private String orderNo;
        private int inOut;
        private String amount;
        private String amountAfter;
        private ExpandBean expand;

        public String getOrderNo()
        {
            return orderNo;
        }

        public void setOrderNo(String orderNo)
        {
            this.orderNo = orderNo;
        }

        public int getInOut()
        {
            return inOut;
        }

        public void setInOut(int inOut)
        {
            this.inOut = inOut;
        }

        public String getAmount()
        {
            return amount;
        }

        public void setAmount(String amount)
        {
            this.amount = amount;
        }

        public String getAmountAfter()
        {
            return amountAfter;
        }

        public void setAmountAfter(String amountAfter)
        {
            this.amountAfter = amountAfter;
        }

        public ExpandBean getExpand()
        {
            return expand;
        }

        public void setExpand(ExpandBean expand)
        {
            this.expand = expand;
        }

        public static class ExpandBean
        {
            /**
             * remark : 派奖
             */

            private String remark;

            public String getRemark()
            {
                return remark;
            }

            public void setRemark(String remark)
            {
                this.remark = remark;
            }
        }
    }
}
