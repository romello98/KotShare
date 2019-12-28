package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.LikeService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.model.Like;
import com.example.kotshare.model.LikeDataModel;
import com.example.kotshare.model.PagedResult;

import java.util.List;

import retrofit2.Call;

public class LikeDAO implements LikeDataAccess
{
    private static LikeDAO likeDAO;
    private LikeService likeService;

    private LikeDAO()
    {
        likeService = ServicesConfiguration.getInstance().getRetrofit().create(LikeService.class);
    }

    public static LikeDAO getInstance()
    {
        if(likeDAO == null) likeDAO = new LikeDAO();
        return likeDAO;
    }

    @Override
    public Call<Like> sendLike(int userId, int studentRoomId) {
        LikeDataModel likeDataModel = new LikeDataModel();
        likeDataModel.setUserId(userId);
        likeDataModel.setStudentRoomId(studentRoomId);
        return likeService.sendLike(likeDataModel);
    }

    @Override
    public Call unlike(int userId, int studentRoomId) {
        return likeService.unlike(userId, studentRoomId);
    }

    @Override
    public boolean isLikedBy(int studentRoomId, int userId) {
        return false;
    }

    @Override
    public Call<List<Integer>> getLikesByUserId(int userId) {
        return likeService.getLikesByUserId(userId);
    }

    @Override
    public Like add(Like like) {
        return null;
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
