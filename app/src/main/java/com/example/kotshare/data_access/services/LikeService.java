package com.example.kotshare.data_access.services;

import com.example.kotshare.model.Like;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LikeService
{
    @GET("Like/{userId}")
    Call<List<Integer>> getLikesByUserId(@Path("userId") Integer userId);
}
