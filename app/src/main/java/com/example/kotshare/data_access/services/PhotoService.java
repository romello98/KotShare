package com.example.kotshare.data_access.services;

import com.example.kotshare.model.Photo;
import com.example.kotshare.model.data_model.PhotoDataModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PhotoService
{
    @POST("Photo")
    Call<Photo> addPhoto(@Body PhotoDataModel photoDataModel);

    @DELETE("/Photo/all/{studentRoomId}")
    Call<Void> deletePhotos(@Path("studentRoomId") Integer studentRoomId);
}
