package com.example.admin.caipiao33.bean;

import java.util.List;

/**
 * TopupBean
 */

public class TopupBean
{

    /**
     * amount : 12448.82
     * rechargeTip : 为避免17:00－21:30高峰入款拥堵，建议各用户提前充值，谢谢！
     * payType : [{"name":"银行转账","code":"bank"},{"name":"微信","code":"wechat"},{"name":"支付宝","code":"alipay"},{"name":"QQ钱包","code":"qqpay"},{"name":"网银支付","code":"online"},{"name":"其它","code":"other"}]
     * code : gavin
     */

    private double amount;
    private String rechargeTip;
    private String code;
    private List<PayTypeBean> payType;

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getRechargeTip()
    {
        return rechargeTip;
    }

    public void setRechargeTip(String rechargeTip)
    {
        this.rechargeTip = rechargeTip;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public List<PayTypeBean> getPayType()
    {
        return payType;
    }

    public void setPayType(List<PayTypeBean> payType)
    {
        this.payType = payType;
    }

    public static class PayTypeBean
    {
        /**
         * name : 银行转账
         * code : bank
         */

        private String name;
        private String code;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }
    }
}
