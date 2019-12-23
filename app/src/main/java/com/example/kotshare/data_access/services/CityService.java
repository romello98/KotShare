package com.example.kotshare.data_access.services;

import com.example.kotshare.model.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CityService
{
    @GET("City")
    Call<List<City>> getAll();
}
