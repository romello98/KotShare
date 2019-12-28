package com.example.kotshare.data_access.services;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.LikeForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LikeService
{
    @GET("Like/{userId}")
    Call<List<Integer>> getLikesByUserId(@Path("userId") Integer userId);

    @DELETE("/Like/{userId}/{studentRoomId}")
    Call<Void> unlike(@Path("userId") int userId, @Path("studentRoomId") int studentRoomId);

    @POST("Like")
    Call<Like> sendLike(@Body LikeForm likeForm);
}
