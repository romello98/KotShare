package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;

public interface LikeDataAccess extends IDataAccess<Like>
{
    boolean hasLikedStudentRoom(User user, StudentRoom studentRoom);
    ArrayList<Like> getLikesOfUser(User user);
    ArrayList<Like> getLikesOfStudentRoom(StudentRoom studentRoom);
    int getLikesNumberOfStudentRoom(StudentRoom studentRoom);
}
