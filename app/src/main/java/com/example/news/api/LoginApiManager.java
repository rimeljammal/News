package com.example.news.api;

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
}
