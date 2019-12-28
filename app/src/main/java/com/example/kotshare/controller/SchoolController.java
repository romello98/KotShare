package com.example.kotshare.controller;

import com.example.kotshare.data_access.SchoolDAO;
import com.example.kotshare.data_access.SchoolDataAccess;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import retrofit2.Call;

public class SchoolController
{
    private SchoolDataAccess schoolDataAccess;

    public SchoolController()
    {
        schoolDataAccess = SchoolDAO.getInstance();
    }

    public Call<PagedResult<School>> getAllSchools(Integer pageIndex, Integer pageSize)
    {
        return schoolDataAccess.getAllSchools(pageIndex, pageSize);
    }

}
