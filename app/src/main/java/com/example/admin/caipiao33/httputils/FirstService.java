package com.example.admin.caipiao33.httputils;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * 用于请求一级标签
 * api/xxx
 */
public interface FirstService
{
    @GET("api/{name}")
    Call<String> getFirstRepos(@Path("name") String name, @QueryMap Map<String, String> options);

    @GET("api/{name}/{second}")
    Call<String> getSecondRepos(@Path("name") String name, @Path("second") String second, @QueryMap Map<String, String> options);

    @GET("api/{name}/{second}/{third}")
    Call<String> getThirdRepos(@Path("name") String name, @Path("second") String second, @Path("third") String third, @QueryMap Map<String, String> options);
}
