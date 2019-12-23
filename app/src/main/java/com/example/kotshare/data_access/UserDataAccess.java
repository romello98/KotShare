package com.example.kotshare.data_access;

import com.example.kotshare.model.User;

import retrofit2.Call;

public interface UserDataAccess extends IDataAccess<User>
{
    Call<User> getUserByEmail(String email);
}
