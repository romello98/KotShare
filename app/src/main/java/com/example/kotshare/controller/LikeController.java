package com.example.kotshare.controller;

import com.example.kotshare.data_access.LikeDAO;
import com.example.kotshare.model.Like;

import java.util.List;

import retrofit2.Call;

public class LikeController
{
    private LikeDAO likeDataAccess;

    public LikeController()
    {
        this.likeDataAccess = LikeDAO.getInstance();
    }

    public Call<Like> sendLike(int userId, int studentRoomId)
    {
        return likeDataAccess.sendLike(userId, studentRoomId);
    }

    public Call unlike(int userId, int studentRoomId)
    {
        return likeDataAccess.unlike(userId, studentRoomId);
    }

    public boolean isLikedBy(int studentRoomId, int userId)
    {
        return likeDataAccess.isLikedBy(studentRoomId, userId);
    }

    public Call<List<Integer>> getLikesByUserId(Integer userId)
    {
        return likeDataAccess.getLikesByUserId(userId);
    }
}