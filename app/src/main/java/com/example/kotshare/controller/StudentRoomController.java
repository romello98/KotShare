package com.example.kotshare.controller;

import com.example.kotshare.data_access.StudentRoomDAO;
import com.example.kotshare.data_access.StudentRoomDataAccess;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;

public class StudentRoomController
{
    private StudentRoomDataAccess studentRoomDataAccess;

    public StudentRoomController()
    {
        this.studentRoomDataAccess = new StudentRoomDAO();
    }

    public ArrayList<StudentRoom> getAllLikedBy(User user)
    {
        return studentRoomDataAccess.getAllLikedBy(user);
    }

    public boolean isLikedBy(StudentRoom studentRoom, User user)
    {
        return studentRoomDataAccess.isLikedBy(studentRoom, user);
    }
}
