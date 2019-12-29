package com.example.kotshare.controller;

import com.example.kotshare.data_access.PhotoDAO;
import com.example.kotshare.data_access.PhotoDataAccess;
import com.example.kotshare.model.Photo;
import com.example.kotshare.model.data_model.PhotoDataModel;

import retrofit2.Call;

public class PhotoController
{
    private PhotoDataAccess photoDataAccess = PhotoDAO.getInstance();

    public Call<Photo> addPhoto(PhotoDataModel photoDataModel)
    {
        return photoDataAccess.addPhoto(photoDataModel);
    }

    public Call<Void> deletePhotos(Integer studentRoomId)
    {
        return photoDataAccess.deletePhotos(studentRoomId);
    }
}
