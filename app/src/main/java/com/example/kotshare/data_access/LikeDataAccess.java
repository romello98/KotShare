package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;

import java.util.List;

import retrofit2.Call;

public interface LikeDataAccess extends IDataAccess<Like>
{
    Call<List<Integer>> getLikesByUserId(int userId);
    Call<Like> sendLike(int userId, int studentRoomId);
    Call unlike(int userId, int studentRoomId);
    boolean isLikedBy(int studentRoomId, int userId);
}
