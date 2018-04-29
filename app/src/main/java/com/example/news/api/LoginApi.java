package com.example.news.api;

import com.example.news.models.ApiResponse;
import com.example.news.models.ArticleItem;
import com.example.news.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Rim on 16/04/2018.
 */

public interface LoginApi {

    @POST("login")
    Call<User> login(@Body User user);

    @POST("register")
    Call<User> register(@Body User user);

    @Multipart
    @POST("/update")
    Call<ApiResponse> updateFavorite(@Part("favString") List<String> favString, @Header("Authorization") String token);
}
