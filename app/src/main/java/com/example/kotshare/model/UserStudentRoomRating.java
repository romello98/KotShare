package com.example.kotshare.model;

public class UserStudentRoomRating
{
    private Integer id;
    private User user;
    private StudentRoom studentRoom;
    private Integer rating;

    public UserStudentRoomRating(){}

    public UserStudentRoomRating(Integer id, User user, StudentRoom studentRoom, Integer rating) {
        setId(id);
        setUser(user);
        setStudentRoom(studentRoom);
        setRating(rating);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StudentRoom getStudentRoom() {
        return studentRoom;
    }

    public void setStudentRoom(StudentRoom studentRoom) {
        this.studentRoom = studentRoom;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        if(rating > 5) this.rating = 5;
        else if (rating < 0) this.rating = 0;
        else this.rating = rating;
    }
}
