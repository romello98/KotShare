package com.example.kotshare.data_access.services;

import com.example.kotshare.model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService
{
    @GET("User/{id}")
    Call<User> getUserById(@Path("id") Integer id);
}
