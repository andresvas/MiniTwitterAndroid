package com.todo.bc.minitwitter.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.todo.bc.minitwitter.common.Constants.API_TWITTER_BASE_URL;

public class MiniTwitterCLient {

    private static MiniTwitterCLient instance = null;
    private MiniTwitterService miniTwitterService;
    private Retrofit retrofit;

    public MiniTwitterCLient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(API_TWITTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        miniTwitterService = retrofit.create(MiniTwitterService.class);
    }


    public static MiniTwitterCLient getInstance() {
        if (instance == null) {
            instance = new MiniTwitterCLient();
        }

        return instance;
    }

    public MiniTwitterService getMiniTwitterService() {
        return miniTwitterService;
    }

}
