package com.example.admin.caipiao33.httputils;

/**
 * Created by lsd on 2016/5/6.
 */
public interface NetErrorCode
{
    /*
    1001	缺少系统参数token
    3002	该版本已不可用,请下载最新版本
    1005	token错误
    3004	token超时
     */ int TOKEN_LACK = 1001;
    //    int TOKEN_DISCARD = 3002;
    //    int TOKEN_TIMEOUT = 3004;
    int TOKEN_ERROR = 1005;

    int NULL_INTEGRAL = 2027;
}
