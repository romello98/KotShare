package com.example.kotshare.model;

public class User
{
    //TODO: compléter la classe

    private String email;
    private String password;
    private Integer id;

    public User() {}
    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String email, String password)
    {
        this(email, password);
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
