package com.example.kotshare.data_access;

import com.example.kotshare.model.City;

import java.util.List;

import retrofit2.Call;

public interface CityDataAccess
{
    Call<List<City>> getAll();
}
