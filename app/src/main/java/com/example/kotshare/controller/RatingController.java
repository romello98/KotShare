package com.example.kotshare.controller;

import com.example.kotshare.data_access.RatingDAO;
import com.example.kotshare.data_access.RatingDataAccess;
import com.example.kotshare.model.Rating;
import com.example.kotshare.model.data_model.RatingDataModel;

import java.util.List;

import retrofit2.Call;

public class RatingController
{
    private RatingDataAccess ratingDataAccess;

    public RatingController()
    {
        this.ratingDataAccess = RatingDAO.getInstance();
    }

    public Call<Rating> setRating(RatingDataModel ratingDataModel)
    {
        return ratingDataAccess.setRating(ratingDataModel);
    }

    public Call<List<Float>> getRatings(Integer studentRoomId)
    {
        return ratingDataAccess.getRatings(studentRoomId);
    }

    public Call<Void> delete(Integer userId, Integer studentRoomId)
    {
        return ratingDataAccess.delete(userId, studentRoomId);
    }

    public Call<Rating> getRating(Integer userId, Integer studentRoomId)
    {
        return ratingDataAccess.getRating(userId, studentRoomId);
    }

}
