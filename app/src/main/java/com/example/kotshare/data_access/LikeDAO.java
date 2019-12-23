package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.LikeService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;

public class LikeDAO implements LikeDataAccess
{
    private static LikeDAO likeDAO;
    private UserDataAccess userDataAccess;
    private StudentRoomDataAccess studentRoomDataAccess;
    private ArrayList<Like> likes;
    private LikeService likeService;

    private LikeDAO()
    {
        likeService = ServicesConfiguration.getInstance().getRetrofit().create(LikeService.class);
    }

    public void init()
    {
/*        this.userDataAccess = UserDAO.getInstance();
        this.studentRoomDataAccess = StudentRoomDAO.getInstance();
        if(likes == null)
            this.likes = new ArrayList<>(
                Arrays.asList(
                        new Like(1, userDataAccess.find(1), studentRoomDataAccess.find(2)),
                        new Like(2, userDataAccess.find(2), studentRoomDataAccess.find(1))
                )
            );*/
    }

    public static LikeDAO getInstance()
    {
        if(likeDAO == null) likeDAO = new LikeDAO();
        return likeDAO;
    }

    @Override
    public Like sendLike(int userId, int studentRoomId) {
/*        User user = userDataAccess.find(userId);
        StudentRoom studentRoom = studentRoomDataAccess.find(studentRoomId);
        Like like = null;
        if(!hasLikedStudentRoom(userId, studentRoomId)) {
            like = new Like(likes.size() + 1, user, studentRoom);
            likes.add(like);
        }
        return like;*/
        return null;
    }

    @Override
    public boolean unlike(int userId, int studentRoomId) {
        /*User user = userDataAccess.find(userId);
        StudentRoom studentRoom = studentRoomDataAccess.find(studentRoomId);
        if(hasLikedStudentRoom(userId, studentRoomId))
        {
            for(Like like : likes)
                if(like.getUser() != null && like.getStudentRoom() != null &&
                        like.getUser().getId() == userId && like.getStudentRoom().getId() == studentRoomId)
                {
                    likes.remove(like);
                    user.getLikes().remove(like);
                    studentRoom.getLikes().remove(like);
                    return true;
                }
        }*/
        return false;
    }

    @Override
    public boolean isLikedBy(int studentRoomId, int userId) {
/*        for(Like like: likes)
            if(like.getStudentRoom().getId() == studentRoomId && like.getUser().getId() == userId)
                return true;*/
        return false;
    }

    @Override
    public boolean hasLikedStudentRoom(int userId, int studentRoomId) {
        for(Like like : likes)
            if(like.getUser().getId() == userId &&
                    studentRoomId == like.getStudentRoom().getId())
                        return true;
        return false;
    }

    @Override
    public Call<List<Integer>> getLikesByUserId(int userId) {
        return likeService.getLikesByUserId(userId);
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
    public Call<PagedResult<Like>> getAll(Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public Call<Like> find(int id) {
        return null;
    }

    @Override
    public Call<PagedResult<Like>> where(Predicate<Like> predicate) {
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
