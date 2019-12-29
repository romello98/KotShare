package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;
import com.example.kotshare.model.form.StudentRoomForm;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;

public interface StudentRoomDataAccess extends IDataAccess<StudentRoom>
{
    ArrayList<StudentRoom> getAllLikedBy(int userId);
    Call<StudentRoom> addStudentRoom(StudentRoomForm studentRoomForm);
    Call<StudentRoom> updateStudentRoom(int id, StudentRoomForm studentRoomForm);
    Call<PagedResult<StudentRoom>> getStudentRooms(Integer pageIndex, Integer pageSize, Integer cityId,
                                                   Integer minPrice, Integer maxPrice, Long startDate,
                                                   Long endDate);

    Call<PagedResult<StudentRoom>> getOwnStudentRooms(Integer userId, Integer pageIndex,
                                                      Integer pageSize);

    Call<PagedResult<StudentRoom>> getFavoriteStudentRooms(Integer userId, Integer pageIndex,
                                                      Integer pageSize);
}
