package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.SchoolService;
import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import java.util.List;

import retrofit2.Call;

public class SchoolDAO implements SchoolDataAccess {

    private SchoolService schoolService;
    private static SchoolDAO schoolDAO;

    public static SchoolDataAccess getInstance() {
        if(schoolDAO == null) schoolDAO = new SchoolDAO();
        schoolDAO.schoolService = ServicesConfiguration.getInstance().getRetrofit()
                .create(SchoolService.class);
        return schoolDAO;
    }

    @Override
    public Call<List<School>> getAll() {
        return schoolService.getAll();
    }
}
