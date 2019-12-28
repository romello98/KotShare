package com.example.kotshare.view.recycler_views;

import com.example.kotshare.controller.LikeController;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.view.SharedPreferencesAccessor;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Util
{
    private static LikeController likeController = new LikeController();

    public static void setLikes(ArrayList<StudentRoom> studentRooms, List<Integer> ids)
    {
        for(StudentRoom studentRoom : studentRooms)
            studentRoom.setLiked(ids.contains(studentRoom.getId()));
    }

    public static Thread getCurrentUserLikesThread(List<Integer> studentRoomsIdsLiked)
    {
        return new Thread(() -> {
            Call<List<Integer>> likesCall = likeController
                    .getLikesByUserId(SharedPreferencesAccessor.getInstance().getUser().getId());
            likesCall.enqueue(new Callback<List<Integer>>() {
                @Override
                public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                    if(response.isSuccessful())
                    {
                        studentRoomsIdsLiked.addAll(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Integer>> call, Throwable t) {

                }
            });
        });
    }
}
