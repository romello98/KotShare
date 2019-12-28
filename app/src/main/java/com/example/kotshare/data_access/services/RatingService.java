package com.example.kotshare.data_access.services;

import com.example.kotshare.model.Rating;
import com.example.kotshare.model.RatingDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RatingService
{
    @POST("Rating")
    Call<Rating> setRating(@Body RatingDataModel ratingDataModel);

    @DELETE("Rating/{userId}/{studentRoomId}")
    Call<Void> delete(@Path("userId") Integer userId, @Path("studentRoomId") Integer studentRoomId);

    @GET("Rating/{studentRoomId}")
    Call<List<Float>> getRatings(@Path("studentRoomId") Integer studentRoomId);

    @GET("Rating/{userId}/{studentRoomId}")
    Call<Rating> getRating(@Path("userId") Integer userId, @Path("studentRoomId") Integer studentRoomId);
}
