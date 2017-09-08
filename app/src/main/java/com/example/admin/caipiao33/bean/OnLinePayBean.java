package com.example.admin.caipiao33.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 网银支付列表
 */

public class OnLinePayBean implements Serializable
{

    /**
     * payUrl : http://localhost/lottery_user
     * minAmount : 1
     * maxAmount : 5000
     * id : 21
     * expand : [{"name":"工商银行（显示）","id":"(提交值)"}]
     */

    private String payUrl;
    private int minAmount;
    private int maxAmount;
    private String id;
    private ArrayList<ExpandBean> expand;

    public String getPayUrl()
    {
        return payUrl;
    }

    public void setPayUrl(String payUrl)
    {
        this.payUrl = payUrl;
    }

    public int getMinAmount()
    {
        return minAmount;
    }

    public void setMinAmount(int minAmount)
    {
        this.minAmount = minAmount;
    }

    public int getMaxAmount()
    {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount)
    {
        this.maxAmount = maxAmount;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public ArrayList<ExpandBean> getExpand()
    {
        return expand;
    }

    public void setExpand(ArrayList<ExpandBean> expand)
    {
        this.expand = expand;
    }

    public static class ExpandBean
    {
        /**
         * name : 工商银行（显示）
         * id : (提交值)
         */

        private String name;
        private String id;
        private boolean isSelete;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public boolean isSelete()
        {
            return isSelete;
        }

        public void setSelete(boolean selete)
        {
            isSelete = selete;
        }
    }
}
