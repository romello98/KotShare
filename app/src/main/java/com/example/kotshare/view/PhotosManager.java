package com.example.kotshare.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.kotshare.controller.PhotoController;
import com.example.kotshare.model.Photo;
import com.example.kotshare.model.data_model.PhotoDataModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class PhotosManager {
    private PhotoController photoController;
    private static final String CLOUD_NAME = "kotshare-henallux";
    private static final String API_KEY = "936588678415836";
    private static final String API_SECRET = "1RHSuGgut7OMqQ0zL4o_HuEDgoo";
    private static final String FORMAT = "png";
    private static final String FOLDER = "/Kotshare";
    private static PhotosManager photosManager;
    private Context context;
    private Cloudinary cloudinary;

    private PhotosManager() {
        cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUD_NAME,
                "api_key", API_KEY, "api_secret", API_SECRET));
        photoController = new PhotoController();
    }

    public static PhotosManager getInstance(Context context) {
        if (photosManager == null) photosManager = new PhotosManager();
        photosManager.context = context;
        return photosManager;
    }

    public void addPhotos(Integer studentRoomId, Uri... fileURIs) throws IOException {
        if (studentRoomId == null) return;
        for (Uri uri : fileURIs)
            addSinglePhoto(studentRoomId, uri);
    }

    private void addSinglePhoto(Integer studentRoomId, Uri uri) throws IOException {
        if (studentRoomId == null) return;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();

        new Thread(() -> {
            try {
                Map uploadResult = cloudinary.uploader().upload(bytes,
                        ObjectUtils.asMap("folder", FOLDER + "/" + studentRoomId));
                String publicId = (String) uploadResult.get("public_id");
                Integer version = (Integer) uploadResult.get("version");
                PhotoDataModel photoDataModel = new PhotoDataModel();

                photoDataModel.setFormat(FORMAT);
                photoDataModel.setName(publicId);
                photoDataModel.setVersion(version);
                photoDataModel.setStudentRoomId(studentRoomId);
                Call<Photo> call = photoController.addPhoto(photoDataModel);
                Response<Photo> response = call.execute();

                String url = getBaseUrl(photoDataModel);
                Log.i("app", "URL ADDED : " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void updatePhotos(boolean newStudentRoom, Integer studentRoomId, Uri... uris)
    {
        if(!newStudentRoom) {
            new Thread(() -> {
                Call<Void> call = photoController.deletePhotos(studentRoomId);
                try {
                    Response<Void> response = call.execute();
                    if (response.isSuccessful()) {
                        addPhotos(studentRoomId, uris);
                    } else {

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        else
        {
            try {
                addPhotos(studentRoomId, uris);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getBaseUrl(PhotoDataModel photoDataModel)
    {
        return "https://res.cloudinary.com/" + CLOUD_NAME + "/image/upload/v" +
                photoDataModel.getVersion() + "/" + photoDataModel.getName() + "." + FORMAT;
    }

    public String getBaseUrl(Integer studentRoomId, Photo photo)
    {
        PhotoDataModel photoDataModel = new PhotoDataModel();
        photoDataModel.setVersion(photo.getVersion());
        photoDataModel.setName(photo.getName());
        photoDataModel.setStudentRoomId(studentRoomId);
        return getBaseUrl(photoDataModel);
    }
}
