package com.example.admin.caipiao33.bean;

/**
 * TokenBean
 */

public class TokenBean
{

    /**
     * memberId : 218 会员ID
     * isLogin : 0 是否登录 0未登录 1已登录
     * memberCode : 会员账号
     * memberName : 会员名称
     */

    private String memberId;
    private int isLogin;
    private String memberCode;
    private String memberName;

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }

    public int getIsLogin()
    {
        return isLogin;
    }

    public void setIsLogin(int isLogin)
    {
        this.isLogin = isLogin;
    }

    public String getMemberCode()
    {
        return memberCode;
    }

    public void setMemberCode(String memberCode)
    {
        this.memberCode = memberCode;
    }

    public String getMemberName()
    {
        return memberName;
    }

    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }
}
