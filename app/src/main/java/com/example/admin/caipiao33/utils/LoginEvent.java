package com.example.admin.caipiao33.utils;

/**
 * Created by mac on 2017/8/23.
 */

public class LoginEvent
{
    private String message;

    public LoginEvent(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }
}
