package com.example.kotshare.model;

public class Like
{
    private Integer id;
    private User user;
    private StudentRoom studentRoom;

    public Like(){}

    public Like(Integer id, User user, StudentRoom studentRoom) {
        setId(id);
        setUser(user);
        setStudentRoom(studentRoom);
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
        this.user.getLikes().add(this);
    }

    public StudentRoom getStudentRoom() {
        return studentRoom;
    }

    public void setStudentRoom(StudentRoom studentRoom) {
        this.studentRoom = studentRoom;
        this.studentRoom.getLikes().add(this);
    }
}
