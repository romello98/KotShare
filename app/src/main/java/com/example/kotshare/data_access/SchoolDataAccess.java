package com.example.kotshare.data_access;

import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import retrofit2.Call;

public interface SchoolDataAccess
{
    Call<PagedResult<School>> getAllSchools(Integer pageIndex, Integer pageSize);
}
