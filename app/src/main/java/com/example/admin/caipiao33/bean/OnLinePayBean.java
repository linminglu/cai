package com.example.admin.caipiao33.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 网银支付列表
 */

public class OnLinePayBean implements Serializable
{

    /**
     * type : 1
     * name : 智付网银2001240101
     * returnType : 2
     * payUrl : http://pay.hmtnb.top
     * payType : 4
     * minAmount : 10
     * maxAmount : 100000
     * addAmount : 0
     * id : 18
     * expand : {"bankList":[{"name":"民生银行","id":"CMBC"},{"name":"工商银行","id":"ICBC"},{"name":"交通银行","id":"BCOM"},{"name":"光大银行","id":"CEBB"},{"name":"中信银行","id":"ECITIC"},{"name":"中国邮政银行","id":"PSBC"},{"name":"浦发银行","id":"SPDB"},{"name":"杭州银行","id":"HZB"},{"name":"华夏银行","id":"HXB"},{"name":"兴业银行","id":"CIB"},{"name":"上海银行","id":"SHB"},{"name":"建设银行","id":"CCB"},{"name":"宁波银行","id":"NBB"},{"name":"中国银行","id":"BOC"},{"name":"农业银行","id":"ABC"},{"name":"浙江稠州商业银行","id":"CZB"},{"name":"平安银行","id":"SPABANK"},{"name":"招商银行","id":"CMB"}]}
     */

    private int type;
    private String name;
    private int returnType;
    private String payUrl;
    private int payType;
    private int minAmount;
    private int maxAmount;
    private int addAmount;
    private String id;
    private ExpandBean expand;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getReturnType()
    {
        return returnType;
    }

    public void setReturnType(int returnType)
    {
        this.returnType = returnType;
    }

    public String getPayUrl()
    {
        return payUrl;
    }

    public void setPayUrl(String payUrl)
    {
        this.payUrl = payUrl;
    }

    public int getPayType()
    {
        return payType;
    }

    public void setPayType(int payType)
    {
        this.payType = payType;
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

    public int getAddAmount()
    {
        return addAmount;
    }

    public void setAddAmount(int addAmount)
    {
        this.addAmount = addAmount;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
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
        private ArrayList<BankListBean> bankList;

        public ArrayList<BankListBean> getBankList()
        {
            return bankList;
        }

        public void setBankList(ArrayList<BankListBean> bankList)
        {
            this.bankList = bankList;
        }

        public static class BankListBean
        {
            /**
             * name : 民生银行
             * id : CMBC
             */

            private String name;
            private String id;
            private boolean isSelete;

            public boolean isSelete()
            {
                return isSelete;
            }

            public void setSelete(boolean selete)
            {
                isSelete = selete;
            }

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
        }
    }
}
