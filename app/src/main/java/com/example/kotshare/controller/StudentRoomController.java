package com.example.kotshare.controller;

import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;

import java.util.ArrayList;

import retrofit2.Call;

public class StudentRoomController
{
    private StudentRoomDataAccess studentRoomDataAccess;

    public StudentRoomController()
    {
        this.studentRoomDataAccess = StudentRoomDAO.getInstance();
    }

    public Call<StudentRoom> find(int studentRoomId)
    {
        return studentRoomDataAccess.find(studentRoomId);
    }

    public Call<PagedResult<StudentRoom>> getStudentRooms(Integer pageIndex, Integer pageSize,
                                                          Integer cityId, Integer minPrice,
                                                          Integer maxPrice, Long startDate,
                                                          Long endDate)
    {
        return studentRoomDataAccess.getStudentRooms(pageIndex, pageSize, cityId, minPrice, maxPrice,
                startDate, endDate);
    }

    public Call<PagedResult<StudentRoom>> getOwnStudentRooms(Integer userId, Integer pageIndex,
                                                             Integer pageSize) {
        return studentRoomDataAccess.getOwnStudentRooms(userId, pageIndex, pageSize);
    }

    public Call<PagedResult<StudentRoom>> getFavoriteStudentRooms(Integer userId, Integer pageIndex,
                                                                  Integer pageSize)
    {
        return studentRoomDataAccess.getFavoriteStudentRooms(userId, pageIndex, pageSize);
    }
}
