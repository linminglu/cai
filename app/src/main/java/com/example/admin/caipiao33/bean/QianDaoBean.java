package com.example.admin.caipiao33.bean;

import java.util.List;

/**
 * 签到Bean
 */

public class QianDaoBean
{

    /**
     * balance : 1.52312886E8
     * percent : [{"min":1000,"max":10000,"value":0.1},{"min":10001,"value":0.11}]
     * rechargeList : [{"value":200,"key":"20170719"},{"value":0,"key":"20170720"},{"value":0,"key":"20170721"},{"color":"red","value":200,"key":"总计"}]
     * giftAmount : 0.0
     */

    private String balance;
    private String giftAmount;
    private List<PercentBean> percent;
    private List<RechargeListBean> rechargeList;

    public String getBalance()
    {
        return balance;
    }

    public void setBalance(String balance)
    {
        this.balance = balance;
    }

    public String getGiftAmount()
    {
        return giftAmount;
    }

    public void setGiftAmount(String giftAmount)
    {
        this.giftAmount = giftAmount;
    }

    public List<PercentBean> getPercent()
    {
        return percent;
    }

    public void setPercent(List<PercentBean> percent)
    {
        this.percent = percent;
    }

    public List<RechargeListBean> getRechargeList()
    {
        return rechargeList;
    }

    public void setRechargeList(List<RechargeListBean> rechargeList)
    {
        this.rechargeList = rechargeList;
    }

    public static class PercentBean
    {
        /**
         * min : 1000
         * max : 10000
         * value : 0.1
         */

        private String min;
        private String max;
        private double value;

        public String getMin()
        {
            return min;
        }

        public void setMin(String min)
        {
            this.min = min;
        }

        public String getMax()
        {
            return max;
        }

        public void setMax(String max)
        {
            this.max = max;
        }

        public double getValue()
        {
            return value;
        }

        public void setValue(double value)
        {
            this.value = value;
        }
    }

    public static class RechargeListBean
    {
        /**
         * value : 200.0
         * key : 20170719
         * color : red
         */

        private String value;
        private String key;
        private String color;

        public String getValue()
        {
            return value;
        }

        public void setValue(String value)
        {
            this.value = value;
        }

        public String getKey()
        {
            return key;
        }

        public void setKey(String key)
        {
            this.key = key;
        }

        public String getColor()
        {
            return color;
        }

        public void setColor(String color)
        {
            this.color = color;
        }
    }
}
