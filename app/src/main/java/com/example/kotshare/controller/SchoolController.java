package com.example.kotshare.controller;

import com.example.kotshare.data_access.SchoolDAO;
import com.example.kotshare.data_access.SchoolDataAccess;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import java.util.List;

import retrofit2.Call;

public class SchoolController
{
    private SchoolDataAccess schoolDataAccess;

    public SchoolController()
    {
        schoolDataAccess = SchoolDAO.getInstance();
    }

    public Call<List<School>> getAll()
    {
        return schoolDataAccess.getAll();
    }

}
