package com.example.kotshare.data_access.services;

import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolService
{
    @GET("School")
    Call<List<School>> getAll();
}
