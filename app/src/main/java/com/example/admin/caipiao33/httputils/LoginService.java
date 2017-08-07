package com.example.admin.caipiao33.httputils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by lsd on 2016/6/14.
 */
public interface LoginService
{
    @GET("api/login/submit")
    Call<String> getLogin(@QueryMap Map<String, String> options);
}
