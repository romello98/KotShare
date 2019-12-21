package com.example.kotshare.model;

import java.util.HashSet;

public class User
{
    private Integer id;
    private String email;
    private String password;
    private String phoneNumber;
    private String lastName;
    private String firstName;
    private School school;
    private HashSet<Like> likes = new HashSet<>();
    private HashSet<StudentRoom> studentRooms = new HashSet<>();
    private HashSet<UserRole> userRoles = new HashSet<>();
    private HashSet<UserStudentRoomRating> studentRoomRatings = new HashSet<>();

    public User() {}

    public User(String email, String password)
    {
        setEmail(email);
        setPassword(password);
    }

    public User(Integer id, String email, String password)
    {
        this(email, password);
        setId(id);
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public HashSet<Like> getLikes() {
        return likes;
    }

    public void setLikes(HashSet<Like> likes) {
        this.likes = likes;
    }

    public HashSet<StudentRoom> getStudentRooms() {
        return studentRooms;
    }

    public void setStudentRooms(HashSet<StudentRoom> studentRooms) {
        this.studentRooms = studentRooms;
    }

    public HashSet<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(HashSet<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public HashSet<UserStudentRoomRating> getStudentRoomRatings() {
        return studentRoomRatings;
    }

    public void setStudentRoomRatings(HashSet<UserStudentRoomRating> studentRoomRatings) {
        this.studentRoomRatings = studentRoomRatings;
    }
}
