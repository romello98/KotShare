package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;

public interface StudentRoomDataAccess extends IDataAccess<StudentRoom>
{
    ArrayList<StudentRoom> getAllLikedBy(User user);
    boolean isLikedBy(StudentRoom studentRoom, User user);
}
