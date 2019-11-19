package com.example.kotshare.data_access;

import com.example.kotshare.model.User;

public interface UserDataAccess
{
    User getUser(int id);
    User getUserByEmail(String email);
}
