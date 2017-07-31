package com.example.admin.caipiao33.httputils;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by lsd on 2016/6/14.
 */
public interface PhotoService
{
    @Multipart
    @POST("app")
    Call<String> uploadPhotos(@PartMap Map<String, RequestBody> photos);
}
