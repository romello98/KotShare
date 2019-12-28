package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.RatingService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.model.Rating;
import com.example.kotshare.model.RatingDataModel;

import java.util.List;

import retrofit2.Call;

public class RatingDAO implements RatingDataAccess {

    private RatingService ratingService;
    private static RatingDAO ratingDAO;

    private RatingDAO()
    {

    }

    public static RatingDAO getInstance()
    {
        if(ratingDAO == null) ratingDAO = new RatingDAO();
        ratingDAO.ratingService = ServicesConfiguration.getInstance().getRetrofit()
                .create(RatingService.class);
        return ratingDAO;
    }

    @Override
    public Call<Rating> setRating(RatingDataModel ratingDataModel) {
        return ratingService.setRating(ratingDataModel);
    }

    @Override
    public Call<List<Float>> getRatings(Integer studentRoomId) {
        return ratingService.getRatings(studentRoomId);
    }

    @Override
    public Call<Void> delete(Integer userId, Integer studentRoomId) {
        return ratingService.delete(userId, studentRoomId);
    }

    @Override
    public Call<Rating> getRating(Integer userId, Integer studentRoomId) {
        return ratingService.getRating(userId, studentRoomId);
    }
}
