package com.example.admin.caipiao33.httputils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lsd on 2016/6/14.
 */
public interface CommonPostService
{

    @GET("/")
    Call<String> getHomePage();

    @GET("/common/appLogin")
    Call<String> getLogin1(@Query("data") String data);
}
