package com.example.kotshare.data_access;

import com.example.kotshare.model.User;

public interface UserDataAccess extends IDataAccess<User>
{
    User getUserByEmail(String email);
}
