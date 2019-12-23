package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;

public interface StudentRoomDataAccess extends IDataAccess<StudentRoom>
{
    ArrayList<StudentRoom> getAllLikedBy(int userId);
    Call<PagedResult<StudentRoom>> getStudentRooms(Integer pageIndex, Integer pageSize, Integer cityId,
                                                   Integer minPrice, Integer maxPrice, Long startDate,
                                                   Long endDate);
}
