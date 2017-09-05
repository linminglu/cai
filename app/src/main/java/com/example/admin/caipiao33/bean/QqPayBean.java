package com.example.admin.caipiao33.bean;

import java.io.Serializable;

/**
 * 微信支付列表
 */

public class QqPayBean implements Serializable
{

    /**
     * type : 1
     * code : WX001
     * name : 微信001
     * payType : 2
     * payName : 微信支付(扫码)1
     * payDesc : 充值金额范围：10-5000，推荐使用，支付后立即到账
     * payMin : 10
     * payMax : 5000
     * payImg : NVL
     * id : 13
     */

    private int type;
    private String code;
    private String name;
    private String bank;
    private int payType;
    private String payName;
    private String payDesc;
    private int payMin;
    private int payMax;
    private String payImg;
    private String id;
    private String payUrl;
    private boolean isSelete;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPayType()
    {
        return payType;
    }

    public void setPayType(int payType)
    {
        this.payType = payType;
    }

    public String getPayName()
    {
        return payName;
    }

    public void setPayName(String payName)
    {
        this.payName = payName;
    }

    public String getPayDesc()
    {
        return payDesc;
    }

    public void setPayDesc(String payDesc)
    {
        this.payDesc = payDesc;
    }

    public int getPayMin()
    {
        return payMin;
    }

    public void setPayMin(int payMin)
    {
        this.payMin = payMin;
    }

    public int getPayMax()
    {
        return payMax;
    }

    public void setPayMax(int payMax)
    {
        this.payMax = payMax;
    }

    public String getPayImg()
    {
        return payImg;
    }

    public void setPayImg(String payImg)
    {
        this.payImg = payImg;
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

    public String getPayUrl()
    {
        return payUrl;
    }

    public void setPayUrl(String payUrl)
    {
        this.payUrl = payUrl;
    }

    public String getBank()
    {
        return bank;
    }

    public void setBank(String bank)
    {
        this.bank = bank;
    }
}
