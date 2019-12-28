package com.example.kotshare.data_access.services;

import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.School;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolService
{
    @GET("School")
    Call<PagedResult<School>> getAllSchools(@Query("pageIndex") Integer pageIndex,
                                            @Query("pageSize") Integer pageSize);
}
