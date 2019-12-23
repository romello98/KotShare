package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public interface LikeDataAccess extends IDataAccess<Like>
{
    boolean hasLikedStudentRoom(int userId, int studentRoomId);
    Call<List<Integer>> getLikesByUserId(int userId);
    ArrayList<Like> getLikesOfStudentRoom(StudentRoom studentRoom);
    int getLikesNumberOfStudentRoom(StudentRoom studentRoom);
    Like sendLike(int userId, int studentRoomId);
    boolean unlike(int userId, int studentRoomId);
    boolean isLikedBy(int studentRoomId, int userId);
}
