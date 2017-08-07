package com.example.admin.caipiao33.httputils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

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
