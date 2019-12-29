package com.example.kotshare.controller;

import com.example.kotshare.data_access.UserDAO;
import com.example.kotshare.data_access.UserDataAccess;
import com.example.kotshare.model.User;
import com.example.kotshare.model.form.UserForm;

import retrofit2.Call;

public class UserController
{
    private UserDataAccess userDataAccess;

    public UserController()
    {
        userDataAccess = UserDAO.getInstance();
    }

    public Call<User> find(int userId)
    {
        userDataAccess = UserDAO.getInstance();
        return userDataAccess.find(userId);
    }

    public Call<User> signup(UserForm userForm)
    {
        return userDataAccess.signup(userForm);
    }

    public Call<Boolean> emailExists(String email)
    {
        return userDataAccess.emailExists(email);
    }
}
