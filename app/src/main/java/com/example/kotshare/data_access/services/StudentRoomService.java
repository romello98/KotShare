package com.example.kotshare.data_access.services;

import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.StudentRoom;
import com.example.kotshare.model.form.StudentRoomForm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudentRoomService
{
    @GET("StudentRoom")
    Call<PagedResult<StudentRoom>> getStudentRooms(@Query("pageIndex") Integer pageIndex,
                                                   @Query("pageSize") Integer pageSize,
                                                   @Query("cityId") Integer cityId,
                                                   @Query("minPrice") Integer minPrice,
                                                   @Query("maxPrice") Integer maxPrice,
                                                   @Query("startDate") Long startDate,
                                                   @Query("endDate") Long endDate);

    @GET("StudentRoom/{id}")
    Call<StudentRoom> getStudentRoomById(@Path("id") Integer id);

    @POST("StudentRoom")
    Call<StudentRoom> addStudentRoom(@Body StudentRoomForm studentRoom);

    @PUT("StudentRoom")
    Call<StudentRoom> updateStudentRoom(@Query("id") Integer id, @Body StudentRoomForm studentRoomForm);

    @GET("StudentRoom/own")
    Call<PagedResult<StudentRoom>> getOwnStudentRooms(@Query("userId") Integer userId,
                                                      @Query("pageIndex") Integer pageIndex,
                                                      @Query("pageSize") Integer pageSize);

    @GET("StudentRoom/favorite")
    Call<PagedResult<StudentRoom>> getFavoriteStudentRooms(@Query("userId") Integer userId,
                                                           @Query("pageIndex") Integer pageIndex,
                                                           @Query("pageSize") Integer pageSize);
}
