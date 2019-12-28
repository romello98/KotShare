package com.example.kotshare.data_access;

import com.example.kotshare.data_access.services.ServicesConfiguration;
import com.example.kotshare.data_access.services.UserService;
import com.example.kotshare.model.PagedResult;
import com.example.kotshare.model.User;
import com.example.kotshare.model.UserForm;

import java.util.ArrayList;

import retrofit2.Call;

public class UserDAO implements UserDataAccess
{
    private static UserDAO userDAO;
    private UserService userService;
    private ArrayList<User> users = new ArrayList<>();

    private UserDAO()
    {

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
    public Call<User> signup(UserForm userForm) {
        return userService.signup(userForm);
    }

    @Override
    public Call<Boolean> emailExists(String email) {
        return userService.emailExists(email);
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
