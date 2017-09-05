package com.example.admin.caipiao33.bean;

/**
 * Created by cxy on 2017/8/18
 * 提款
 */

public class TiKuanBean
{
    /**
     * status : 1
     * amount : 21054.46
     * accountCode : 324***432
     * accountName : 123
     * needDml : 21446.39
     * realDml : 130
     * lastRecharge : 21140.59
     * freeTimes : 无限次
     * bankName : 中国农业银行
     * canWithdraw : 否
     * withdrawTip : XXX
     */

    private String status;
    private String amount;
    private String accountCode;
    private String accountName;
    private String needDml;
    private String realDml;
    private String lastRecharge;
    private String freeTimes;
    private String bankName;
    private String canWithdraw;
    private String withdrawTip;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
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

    public String getNeedDml()
    {
        return needDml;
    }

    public void setNeedDml(String needDml)
    {
        this.needDml = needDml;
    }

    public String getRealDml()
    {
        return realDml;
    }

    public void setRealDml(String realDml)
    {
        this.realDml = realDml;
    }

    public String getLastRecharge()
    {
        return lastRecharge;
    }

    public void setLastRecharge(String lastRecharge)
    {
        this.lastRecharge = lastRecharge;
    }

    public String getFreeTimes()
    {
        return freeTimes;
    }

    public void setFreeTimes(String freeTimes)
    {
        this.freeTimes = freeTimes;
    }

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getCanWithdraw()
    {
        return canWithdraw;
    }

    public void setCanWithdraw(String canWithdraw)
    {
        this.canWithdraw = canWithdraw;
    }

    public String getWithdrawTip()
    {
        return withdrawTip;
    }

    public void setWithdrawTip(String withdrawTip)
    {
        this.withdrawTip = withdrawTip;
    }
}
