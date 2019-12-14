package com.example.kotshare.model;

public class StudentRoom
{
    private Integer id;

    private String title;

    private String description;

    private User holder;

    public User getHolder() {
        return holder;
    }

    public StudentRoom(Integer id, String title, String description, User holder)
    {
        setId(id);
        setTitle(title);
        setDescription(description);
        setHolder(holder);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHolder(User holder) {
        this.holder = holder;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
