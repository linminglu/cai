package com.example.admin.caipiao33.bean;

/**
 * 支付宝银行卡
 */

public class AliBankBean
{
    /**
     * amount : 101
     * id : 13
     * orderNo : 1503120468571744
     * accountCode : 12143242
     * accountName : 测试
     * bankName : 招商银行
     * tip : 为方便客服入账,请在转账备注填写您的会员号,谢谢!
     * isShow : 1
     */

    private String amount;
    private String id;
    private String orderNo;
    private String accountCode;
    private String accountName;
    private String bankName;
    private String tip;
    private String isShow;

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getAccountCode()
    {
        return accountCode;
    }

    public void setAccountCode(String accountCode)
    {
        this.accountCode = accountCode;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getTip()
    {
        return tip;
    }

    public void setTip(String tip)
    {
        this.tip = tip;
    }

    public String getIsShow()
    {
        return isShow;
    }

    public void setIsShow(String isShow)
    {
        this.isShow = isShow;
    }
}
