package com.example.kotshare.data_access;

import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import java.util.List;

import retrofit2.Call;

public interface SchoolDataAccess
{
    Call<List<School>> getAll();
}
