package com.example.kotshare.model;

import java.util.HashSet;

public class School
{
    private Integer id;
    private String name;
    private HashSet<User> users = new HashSet<>();

    public School() { }

    public School(Integer id, String name, HashSet<User> users) {
        setId(id);
        setName(name);
        setUsers(users);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<User> getUsers() {
        return users;
    }

    public void setUsers(HashSet<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return name == null ? "École inconnue" : name;
    }
}
