package com.example.news.api;

import com.example.news.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rim on 07/03/2018.
 */

public interface NewsApi {

    @GET("top-headlines")
    Call<ApiResponse> getRandom(@Query("country") String random);

    @GET("everything")
    Call<ApiResponse> getArticles(@Query("q") String q);
}