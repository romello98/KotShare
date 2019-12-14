package com.example.kotshare.model;

public class StudentRoom
{
    private Integer id;

    private String title;

    private Double price;

    private User holder;

    public User getHolder() {
        return holder;
    }

    public StudentRoom(Integer id, String title, Double price, User holder)
    {
        setId(id);
        setTitle(title);
        setPrice(price);
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
