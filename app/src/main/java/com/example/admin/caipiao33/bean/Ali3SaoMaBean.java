package com.example.admin.caipiao33.bean;

/**
 * 微信第三方扫码支付
 */

public class Ali3SaoMaBean
{

    /**
     * amount : 100.52
     * needDown : 0
     * orderNo : 1502959238337230
     * payUrl : https://myun.tenpay.com/mqq/pay/qrcode.html?_wv=1027&_bid=2183&t=5V80f7d7589207dee3af721039e2333f
     * errorTip : 如遇无法充值请降低金额或点击上一步更换其他充值方式
     * steps : *扫码步骤：1.请自行截屏或保存二维码图片到相册，同时打开QQ钱包。2.请在QQ钱包中打开“扫一扫”。3.在扫一扫中点击右上解，选择“从相册选择二维码”选取截屏的图片。4.输入您欲充值的金额并进行转账。如充值未及时到账，请联系在线客服。5.在QQ钱包支付完成后，请点击“我已支付”提交审核。
     */

    private String amount;
    private String needDown;
    private String orderNo;
    private String payUrl;
    private String errorTip;
    private String steps;

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getNeedDown()
    {
        return needDown;
    }

    public void setNeedDown(String needDown)
    {
        this.needDown = needDown;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getPayUrl()
    {
        return payUrl;
    }

    public void setPayUrl(String payUrl)
    {
        this.payUrl = payUrl;
    }

    public String getErrorTip()
    {
        return errorTip;
    }

    public void setErrorTip(String errorTip)
    {
        this.errorTip = errorTip;
    }

    public String getSteps()
    {
        return steps;
    }

    public void setSteps(String steps)
    {
        this.steps = steps;
    }
}
