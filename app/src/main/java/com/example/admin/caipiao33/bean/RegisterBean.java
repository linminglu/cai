package com.example.admin.caipiao33.bean;

/**
 * 注册Bean
 */

public class RegisterBean
{

    /**
     * phone : 0
     * email : 0
     * qq : 0
     * verifyCode : 7716
     * webName : 彩票89
     * dhUrl : www.cp89.com
     */

    private int phone;
    private int email;
    private int qq;
    private String verifyCode;
    private String webName;
    private String dhUrl;

    public int getPhone()
    {
        return phone;
    }

    public void setPhone(int phone)
    {
        this.phone = phone;
    }

    public int getEmail()
    {
        return email;
    }

    public void setEmail(int email)
    {
        this.email = email;
    }

    public int getQq()
    {
        return qq;
    }

    public void setQq(int qq)
    {
        this.qq = qq;
    }

    public String getVerifyCode()
    {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode)
    {
        this.verifyCode = verifyCode;
    }

    public String getWebName()
    {
        return webName;
    }

    public void setWebName(String webName)
    {
        this.webName = webName;
    }

    public String getDhUrl()
    {
        return dhUrl;
    }

    public void setDhUrl(String dhUrl)
    {
        this.dhUrl = dhUrl;
    }
}
