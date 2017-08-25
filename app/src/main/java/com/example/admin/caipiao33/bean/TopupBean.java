package com.example.admin.caipiao33.bean;

/**
 * TopupBean
 */

public class TopupBean
{

    /**
     * amount : 22805.46
     * rechargeTip : 为避免17:00-21:30高峰入款拥堵,彩票89建议各用户提前充值！
     * payType : {"bank":"银行转账","wechat":"微信","alipay":"支付宝","qqpay":"QQ钱包","online":"网银支付"}
     * code : gavin
     */

    private String amount;
    private String rechargeTip;
    private PayTypeBean payType;
    private String code;

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
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

    public PayTypeBean getPayType()
    {
        return payType;
    }

    public void setPayType(PayTypeBean payType)
    {
        this.payType = payType;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public static class PayTypeBean
    {
        /**
         * bank : 银行转账
         * wechat : 微信
         * alipay : 支付宝
         * qqpay : QQ钱包
         * online : 网银支付
         */

        private String bank;
        private String wechat;
        private String alipay;
        private String qqpay;
        private String online;

        public String getBank()
        {
            return bank;
        }

        public void setBank(String bank)
        {
            this.bank = bank;
        }

        public String getWechat()
        {
            return wechat;
        }

        public void setWechat(String wechat)
        {
            this.wechat = wechat;
        }

        public String getAlipay()
        {
            return alipay;
        }

        public void setAlipay(String alipay)
        {
            this.alipay = alipay;
        }

        public String getQqpay()
        {
            return qqpay;
        }

        public void setQqpay(String qqpay)
        {
            this.qqpay = qqpay;
        }

        public String getOnline()
        {
            return online;
        }

        public void setOnline(String online)
        {
            this.online = online;
        }
    }
}
