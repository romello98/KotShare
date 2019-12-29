package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.PhotoService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.model.Photo;
import com.example.kotshare.model.data_model.PhotoDataModel;

import retrofit2.Call;

public class PhotoDAO implements PhotoDataAccess
{
    private PhotoService photoService;
    private static PhotoDAO photoDAO;

    private PhotoDAO()
    {

    }

    public static PhotoDAO getInstance()
    {
        if(photoDAO == null) photoDAO = new PhotoDAO();
        photoDAO.photoService = ServicesConfiguration.getInstance().getRetrofit()
                .create(PhotoService.class);
        return photoDAO;
    }

    @Override
    public Call<Photo> addPhoto(PhotoDataModel photoDataModel) {
        return photoService.addPhoto(photoDataModel);
    }

    @Override
    public Call<Void> deletePhotos(Integer studentRoomId) {
        return photoService.deletePhotos(studentRoomId);
    }
}
