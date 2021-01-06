package com.todo.bc.minitwitter.retrofit;


import com.todo.bc.minitwitter.common.Constants;
import com.todo.bc.minitwitter.common.SharePrefencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = SharePrefencesManager.getSharePreferenceString(Constants.PREF_TOKEN);

        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+ token).build();
        return chain.proceed(request);
    }
}
