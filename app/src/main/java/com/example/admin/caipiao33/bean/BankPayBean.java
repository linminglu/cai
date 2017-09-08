package com.example.admin.caipiao33.bean;

import java.io.Serializable;

/**
 * 银行支付列表
 */

public class BankPayBean implements Serializable
{

    /**
     * bankName : 招商银行
     * bankAddr : 厦门
     * accountCode : 12143242
     * accountName : 测试
     * payMin : 1
     * payMax : 1000
     */

    private String bankName;
    private String bankAddr;
    private String accountCode;
    private String accountName;
    private String id;
    private int payMin;
    private int payMax;
    private boolean isSelete;

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankAddr()
    {
        return bankAddr;
    }

    public void setBankAddr(String bankAddr)
    {
        this.bankAddr = bankAddr;
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

    public boolean isSelete()
    {
        return isSelete;
    }

    public void setSelete(boolean selete)
    {
        isSelete = selete;
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
