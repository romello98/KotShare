package com.example.kotshare.controller;

import com.example.kotshare.data_access.CityDAO;
import com.example.kotshare.data_access.CityDataAccess;
import com.example.kotshare.model.City;

import java.util.List;

import retrofit2.Call;

public class CityController {

    private CityDataAccess cityDataAccess;

    public CityController()
    {
        this.cityDataAccess = CityDAO.getInstance();
    }

    public Call<List<City>> getAll()
    {
        return cityDataAccess.getAll();
    }
}
