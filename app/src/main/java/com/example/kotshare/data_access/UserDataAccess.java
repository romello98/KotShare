package com.example.kotshare.data_access;

import com.example.kotshare.model.User;
import com.example.kotshare.model.form.UserForm;

import retrofit2.Call;

public interface UserDataAccess extends IDataAccess<User>
{
    Call<User> getUserByEmail(String email);
    Call<User> signup(UserForm userForm);
    Call<Boolean> emailExists(String email);
}
