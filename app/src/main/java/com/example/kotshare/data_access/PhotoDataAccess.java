package com.example.kotshare.data_access;

import com.example.kotshare.model.Photo;
import com.example.kotshare.model.data_model.PhotoDataModel;

import retrofit2.Call;

public interface PhotoDataAccess
{
    Call<Photo> addPhoto(PhotoDataModel photoDataModel);
    Call<Void> deletePhotos(Integer studentRoomId);
}
