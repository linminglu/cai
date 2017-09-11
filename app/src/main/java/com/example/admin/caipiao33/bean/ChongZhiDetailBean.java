package com.example.admin.caipiao33.bean;

import java.io.Serializable;

/**
 * 充值详情
 */

public class ChongZhiDetailBean implements Serializable
{

    /**
     * amount : 1.00
     * fAmount : 1.00
     * orderNo : 1500650803383305
     * saveTime : 2017-07-21 23:26:43
     * remark : 无效订单
     * status : -1
     * giftAmount : 0.00
     * type : 支付宝入款
     * addAmount : 0.00
     */

    private String amount;
    private String fAmount;
    private String orderNo;
    private String saveTime;
    private String remark;
    private int status;
    private String giftAmount;
    private String type;
    private String addAmount;

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getFAmount()
    {
        return fAmount;
    }

    public void setFAmount(String fAmount)
    {
        this.fAmount = fAmount;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getSaveTime()
    {
        return saveTime;
    }

    public void setSaveTime(String saveTime)
    {
        this.saveTime = saveTime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getGiftAmount()
    {
        return giftAmount;
    }

    public void setGiftAmount(String giftAmount)
    {
        this.giftAmount = giftAmount;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getAddAmount()
    {
        return addAmount;
    }

    public void setAddAmount(String addAmount)
    {
        this.addAmount = addAmount;
    }
}
