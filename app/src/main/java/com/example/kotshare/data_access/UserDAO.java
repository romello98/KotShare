package com.example.kotshare.data_access;

import com.example.kotshare.model.User;

import java.util.ArrayList;

public class UserDAO implements UserDataAccess
{
    private static ArrayList<User> users = new ArrayList<>();

    public UserDAO()
    {
        //TODO: A supprimer en production
        users.add(new User(1, "john.doe@gmail.com", "$2a$04$hefrFckyV/5NsEitV9.KjudH4Ra6oypZxM6n3zDPwxwza6IahZ8/."));
        users.add(new User(2, "jane.doe@gmail.com", "$2a$04$hefrFckyV/5NsEitV9.KjudH4Ra6oypZxM6n3zDPwxwza6IahZ8/."));
    }

    @Override
    public User getUserByEmail(String email) {
        for(User currentUser : users)
            if(currentUser.getEmail().equals(email)) return currentUser;

        return null;
    }

    @Override
    public User add(User object) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public User find(int id) {
        for(User currentUser : users)
            if(currentUser.getId() == id) return currentUser;

        return null;
    }

    @Override
    public ArrayList<User> where(Predicate<User> predicate) {
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
