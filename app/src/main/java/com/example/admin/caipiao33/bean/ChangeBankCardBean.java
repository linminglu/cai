package com.example.admin.caipiao33.bean;

/**
 * 银行卡
 */

public class ChangeBankCardBean
{

    /**
     * bankName : 浦发银行
     * accountCode : 354***211
     * accountName : 123
     * provice : 12
     * city : 12
     * expand : {"isWithdrawPasswdSet":1}
     */

    private String bankName;
    private String accountCode;
    private String accountName;
    private String provice;
    private String city;
    private ExpandBean expand;

    public String getBankName()
    {
        return bankName;
    }

    public void setBankName(String bankName)
    {
        this.bankName = bankName;
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

    public String getProvice()
    {
        return provice;
    }

    public void setProvice(String provice)
    {
        this.provice = provice;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
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
         * isWithdrawPasswdSet : 1
         */

        private int isWithdrawPasswdSet;

        public int getIsWithdrawPasswdSet()
        {
            return isWithdrawPasswdSet;
        }

        public void setIsWithdrawPasswdSet(int isWithdrawPasswdSet)
        {
            this.isWithdrawPasswdSet = isWithdrawPasswdSet;
        }
    }
}
