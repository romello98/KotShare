package com.example.kotshare.model;

import java.util.HashSet;

public class Role
{
    private Integer id;
    private String name;
    private HashSet<UserRole> userRoles = new HashSet<>();

    public Role() { }

    public Role(Integer id, String name) {
        setId(id);
        setName(name);
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

    public HashSet<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(HashSet<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
