package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.CityService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.model.City;

import java.util.List;

import retrofit2.Call;

public class CityDAO implements CityDataAccess
{
    private static CityDAO cityDAO;
    private CityService cityService;

    private CityDAO()
    {
        this.cityService = ServicesConfiguration.getInstance().getRetrofit()
                .create(CityService.class);
    }

    public static CityDAO getInstance()
    {
        if(cityDAO == null) cityDAO = new CityDAO();
        return cityDAO;
    }

    @Override
    public Call<List<City>> getAll() {
        return cityService.getAll();
    }
}
