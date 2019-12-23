package com.example.kotshare.data_access.services;

import com.example.kotshare.model.Login;
import com.example.kotshare.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService
{
    @POST("Jwt")
    Call<Token> getToken(@Body Login login);
}
