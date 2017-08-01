package com.example.admin.caipiao33.httputils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * 用于请求一级标签
 * api/xxx
 */
public interface FirstService
{
    @GET("api/{name}")
    Call<String> getFirstRepos(@Path("name") String name);

}
