package com.example.kotshare.controller;

import com.example.kotshare.data_access.UserDAO;
import com.example.kotshare.data_access.UserDataAccess;
import com.example.kotshare.model.User;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserController
{
    private UserDataAccess userDataAccess;

    public UserController()
    {
        userDataAccess = new UserDAO();
    }

    public User getUserByEmail(String email)
    {
        return userDataAccess.getUserByEmail(email);
    }

    public boolean isCorrectPassword(User user, String password)
    {
        return BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified;
    }

    public String encryptPassword(String password)
    {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, password.toCharArray());
    }
}
