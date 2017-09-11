package com.example.admin.caipiao33.bean;

/**
 * 提款详情
 */

public class TiKuanDetailBean
{

    /**
     * orderNo : 1500559825291754
     * accountCode : 354451221211
     * amount : 100.0
     * fee : 0.0
     * feeAdmin : 0.0
     * status : 0
     * addTime : 2017-07-22 10:10:25
     */

    private String orderNo;
    private String accountCode;
    private String amount;
    private String fee;
    private String feeAdmin;
    private int status;
    private String addTime;

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

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getFee()
    {
        return fee;
    }

    public void setFee(String fee)
    {
        this.fee = fee;
    }

    public String getFeeAdmin()
    {
        return feeAdmin;
    }

    public void setFeeAdmin(String feeAdmin)
    {
        this.feeAdmin = feeAdmin;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
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
