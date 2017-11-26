package com.example.admin.caipiao33.bean;

import java.io.Serializable;

/**
 * 走势彩种列表bean
 */

public class TypeBean implements Serializable
{

    /**
     * num : 53
     * code : jsssc
     * name : 极速时时彩
     */

    private String num;
    private String code;
    private String name;

    public String getNum()
    {
        return num;
    }

    public void setNum(String num)
    {
        this.num = num;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
