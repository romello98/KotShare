package com.example.kotshare.data_access;

import com.example.kotshare.model.Like;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class LikeDAO implements LikeDataAccess
{
    private UserDataAccess userDataAccess;
    private StudentRoomDataAccess studentRoomDataAccess;
    private ArrayList<Like> likes;

    public LikeDAO()
    {
        this.userDataAccess = new UserDAO();
        this.studentRoomDataAccess = new StudentRoomDAO();
        this.likes = new ArrayList<>(
            Arrays.asList(
                new Like(1, userDataAccess.find(1), studentRoomDataAccess.find(2)),
                new Like(2, userDataAccess.find(2), studentRoomDataAccess.find(1))
            )
        );
    }

    @Override
    public boolean hasLikedStudentRoom(User user, StudentRoom studentRoom) {
        for(Like like : likes)
            if(like.getUser().getId() == user.getId() &&
                    studentRoom.getId() == like.getStudentRoom().getId())
                        return true;
        return false;
    }

    @Override
    public ArrayList<Like> getLikesOfUser(User user) {
        ArrayList<Like> likes = new ArrayList<>();
        for(Like like : likes)
            if(like.getUser().getId() == user.getId())
                likes.add(like);
        return likes;
    }

    @Override
    public ArrayList<Like> getLikesOfStudentRoom(StudentRoom studentRoom) {
        return null;
    }

    @Override
    public int getLikesNumberOfStudentRoom(StudentRoom studentRoom) {
        int likesNumber = 0;
        for(Like like: likes)
            if(like.getStudentRoom().getId() == studentRoom.getId())
                likesNumber++;
        return likesNumber;
    }

    @Override
    public Like add(Like like) {
        likes.add(like);
        return like;
    }

    @Override
    public ArrayList<Like> getAll() {
        return this.likes;
    }

    @Override
    public Like find(int id) {
        return null;
    }

    @Override
    public ArrayList<Like> where(Predicate<Like> predicate) {
        return null;
    }

    @Override
    public Like update(Like object) {
        return null;
    }

    @Override
    public void delete(Like object) {

    }
}
