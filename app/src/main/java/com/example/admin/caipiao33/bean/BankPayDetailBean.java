package com.example.admin.caipiao33.bean;

/**
 * 银行支付内页
 */

public class BankPayDetailBean
{

    /**
     * orderNo : 1502886575149122
     * saveTime : 2017-08-16 20:29:35
     * money : 100
     * bank : {"bankName":"招商银行","bankAddr":"厦门","accountCode":"12143242","accountName":"测试","payMin":1,"payMax":1000,"id":6}
     */

    private String orderNo;
    private String saveTime;
    private String money;
    private BankBean bank;

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

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }

    public BankBean getBank()
    {
        return bank;
    }

    public void setBank(BankBean bank)
    {
        this.bank = bank;
    }

    public static class BankBean
    {
        /**
         * bankName : 招商银行
         * bankAddr : 厦门
         * accountCode : 12143242
         * accountName : 测试
         * payMin : 1
         * payMax : 1000
         * id : 6
         */

        private String bankName;
        private String bankAddr;
        private String accountCode;
        private String accountName;
        private int payMin;
        private int payMax;
        private String id;

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
