package com.example.kotshare.data_access.services;

import com.example.kotshare.model.User;
import com.example.kotshare.model.UserForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService
{
    @GET("User/{id}")
    Call<User> getUserById(@Path("id") Integer id);

    @POST("User")
    Call<User> signup(@Body UserForm userForm);
}
