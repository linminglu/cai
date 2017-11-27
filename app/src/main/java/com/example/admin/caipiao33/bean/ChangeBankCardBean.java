package com.example.admin.caipiao33.bean;

/**
 * 银行卡
 */

public class ChangeBankCardBean
{

    /**
     * memberId : 75
     * bankName : 中国建设银行
     * accountCode : 124***766
     * accountName : 测试1
     * provice : 厦门
     * city : 厦门
     * remark : 1
     * addTime : May 8, 2017 9:13:59 PM
     * updTime : Nov 23, 2017 2:29:57 PM
     * updBy : gavin
     * id : 16
     * expand : {"isWithdrawPasswdSet":1}
     */

    private int memberId;
    private String bankName;
    private String accountCode;
    private String accountName;
    private String provice;
    private String city;
    private String remark;
    private String addTime;
    private String updTime;
    private String updBy;
    private int id;
    private ExpandBean expand;

    public int getMemberId()
    {
        return memberId;
    }

    public void setMemberId(int memberId)
    {
        this.memberId = memberId;
    }

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

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getAddTime()
    {
        return addTime;
    }

    public void setAddTime(String addTime)
    {
        this.addTime = addTime;
    }

    public String getUpdTime()
    {
        return updTime;
    }

    public void setUpdTime(String updTime)
    {
        this.updTime = updTime;
    }

    public String getUpdBy()
    {
        return updBy;
    }

    public void setUpdBy(String updBy)
    {
        this.updBy = updBy;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
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
