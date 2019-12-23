package com.example.kotshare.data_access;

import android.util.Log;

import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.data_access.services.UserService;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.User;

import java.util.ArrayList;

import retrofit2.Call;

public class UserDAO implements UserDataAccess
{
    private static UserDAO userDAO;
    private UserService userService;
    private ArrayList<User> users = new ArrayList<>();

    private UserDAO()
    {
        /*//TODO: A supprimer en production
        users.add(new User(1, "john.doe@gmail.com", "$2a$04$hefrFckyV/5NsEitV9.KjudH4Ra6oypZxM6n3zDPwxwza6IahZ8/."));
        users.add(new User(2, "jane.doe@gmail.com", "$2a$04$hefrFckyV/5NsEitV9.KjudH4Ra6oypZxM6n3zDPwxwza6IahZ8/."));
        */
    }

    public static UserDAO getInstance()
    {
        if(userDAO == null) userDAO = new UserDAO();
        userDAO.userService = ServicesConfiguration.getInstance().getRetrofit().create(UserService.class);
        return userDAO;
    }

    @Override
    public Call<User> getUserByEmail(String email) {
        return null;
    }

    @Override
    public User add(User object) {
        return null;
    }

    @Override
    public Call<PagedResult<User>> getAll(Integer pageIndex, Integer pageSize) {
        return null;
    }

    public Call<PagedResult<User>> getAll() {
        return null;
    }

    @Override
    public Call<User> find(int id) {
        Log.i("app", "TOKEN RETRIEVED: " );
        return userService.getUserById(id);
    }

    @Override
    public Call<PagedResult<User>> where(Predicate<User> predicate) {
        return null;
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public void delete(User object) {

    }
}
