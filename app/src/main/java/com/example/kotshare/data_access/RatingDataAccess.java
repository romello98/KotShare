package com.example.kotshare.data_access;

import com.example.kotshare.model.Rating;
import com.example.kotshare.model.data_model.RatingDataModel;

import java.util.List;

import retrofit2.Call;

public interface RatingDataAccess
{
    Call<Rating> setRating(RatingDataModel ratingDataModel);
    Call<List<Float>> getRatings(Integer studentRoomId);
    Call<Void> delete(Integer userId, Integer studentRoomId);
    Call<Rating> getRating(Integer userId, Integer studentRoomId);
}
