package com.example.news.api;

import com.example.news.models.ApiResponse;
import com.example.news.models.ArticleItem;
import com.example.news.models.User;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rim on 16/04/2018.
 */

public class LoginApiManager {

    private Retrofit retrofit;
    private LoginApi loginApi;


    private static LoginApiManager loginApiManager;

    private LoginApiManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginApi = retrofit.create(LoginApi.class);
    }

    public static LoginApiManager getInstance() {
        if (loginApiManager == null) {
            loginApiManager = new LoginApiManager();
        }
        return loginApiManager;
    }

    public Call<User> login(User user) {
        return loginApi.login(user);
    }

    public Call<User> register(User user)   {
        return loginApi.register(user);
    }

    public Call<ApiResponse> updateFavorite(List<String> favString, String token) {
        return loginApi.updateFavorite(favString, token);
    }

}
