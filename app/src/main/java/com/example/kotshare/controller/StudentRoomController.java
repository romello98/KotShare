package com.example.kotshare.controller;

import com.example.kotshare.data_access.IDataAccess;
import com.example.kotshare.data_access.LikeDAO;
import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.data_access.UserDAO;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;

public class StudentRoomController
{
    private StudentRoomDAO studentRoomDAO;

    public StudentRoomController()
    {
        this.studentRoomDAO = StudentRoomDAO.getInstance();
        this.studentRoomDAO.init();
    }

    public Call<StudentRoom> find(int studentRoomId)
    {
        return studentRoomDAO.find(studentRoomId);
    }

    public Call<PagedResult<StudentRoom>> getStudentRooms(Integer pageIndex, Integer pageSize,
                                                          Integer cityId, Integer minPrice,
                                                          Integer maxPrice, Long startDate,
                                                          Long endDate)
    {
        return studentRoomDAO.getStudentRooms(pageIndex, pageSize, cityId, minPrice, maxPrice,
                startDate, endDate);
    }

    public Call<PagedResult<StudentRoom>> where(IDataAccess.Predicate<StudentRoom> predicate)
    {
        return studentRoomDAO.where(predicate);
    }

    public Call<PagedResult<StudentRoom>> getAll(Integer pageIndex, Integer pageSize)
    {
        return studentRoomDAO.getAll(pageIndex, pageSize);
    }

    public ArrayList<StudentRoom> getAllLikedBy(int userId)
    {
        return studentRoomDAO.getAllLikedBy(userId);
    }
}
