package com.todo.bc.minitwitter.retrofit;

import com.todo.bc.minitwitter.model.RequestLogin;
import com.todo.bc.minitwitter.model.RequestSignOut;
import com.todo.bc.minitwitter.model.ResponseAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MiniTwitterService {

    @POST("auth/login")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);


    @POST("auth/signup")
    Call<ResponseAuth> doSingOut(@Body RequestSignOut requestSignOut);

}
