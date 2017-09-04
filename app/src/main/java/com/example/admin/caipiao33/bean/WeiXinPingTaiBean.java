package com.example.admin.caipiao33.bean;

/**
 * 微信平台支付
 */

public class WeiXinPingTaiBean
{

    /**
     * amount : 101
     * id : 13
     * errorTip : 如遇无法充值请降低金额或点击上一步更换其他充值方式
     * orderNo : 1503120468571744
     * name : 微信001
     * img : xx.png
     * steps : 1.请自行截屏或保存二维码图片到相册，同时打开微信.....
     * code : WX001
     * tip : 为方便客服入账,请在转账备注填写您的会员号,谢谢!
     * isShow : 1
     */

    private String amount;
    private String id;
    private String errorTip;
    private String orderNo;
    private String name;
    private String img;
    private String steps;
    private String code;
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

    public String getErrorTip()
    {
        return errorTip;
    }

    public void setErrorTip(String errorTip)
    {
        this.errorTip = errorTip;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImg()
    {
        return img;
    }

    public void setImg(String img)
    {
        this.img = img;
    }

    public String getSteps()
    {
        return steps;
    }

    public void setSteps(String steps)
    {
        this.steps = steps;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
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
