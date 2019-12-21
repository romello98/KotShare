package com.example.kotshare.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class StudentRoom
{
    private Integer id;
    private String title;
    private String description;
    private Double monthlyPrice;
    private Boolean hasPersonalBathroom;
    private Boolean hasPersonalKitchen;
    private Boolean hasGarden;
    private Boolean hasFreeWifi;
    private Integer roommatesNumber;
    private Boolean isHidden;
    private Date startRentingDate;
    private Date endRatingDate;
    private String city;
    private String address;
    private User holder;
    private HashSet<Like> likes = new HashSet<>();
    private HashSet<Photo> photos = new HashSet<>();
    private HashSet<UserStudentRoomRating> userStudentRoomRatings = new HashSet<>();

    public User getHolder() {
        return holder;
    }

    public StudentRoom() {}

    public StudentRoom(Integer id, String title, Double monthlyPrice, User holder)
    {
        setId(id);
        setTitle(title);
        setMonthlyPrice(monthlyPrice);
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

    public Double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getHasPersonalBathroom() {
        return hasPersonalBathroom;
    }

    public void setHasPersonalBathroom(Boolean hasPersonalBathroom) {
        this.hasPersonalBathroom = hasPersonalBathroom;
    }

    public Boolean getHasPersonalKitchen() {
        return hasPersonalKitchen;
    }

    public void setHasPersonalKitchen(Boolean hasPersonalKitchen) {
        this.hasPersonalKitchen = hasPersonalKitchen;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public Boolean getHasFreeWifi() {
        return hasFreeWifi;
    }

    public void setHasFreeWifi(Boolean hasFreeWifi) {
        this.hasFreeWifi = hasFreeWifi;
    }

    public Integer getRoommatesNumber() {
        return roommatesNumber;
    }

    public void setRoommatesNumber(Integer roommatesNumber) {
        this.roommatesNumber = roommatesNumber;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Date getStartRentingDate() {
        return startRentingDate;
    }

    public void setStartRentingDate(Date startRentingDate) {
        this.startRentingDate = startRentingDate;
    }

    public Date getEndRatingDate() {
        return endRatingDate;
    }

    public void setEndRatingDate(Date endRatingDate) {
        this.endRatingDate = endRatingDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HashSet<Like> getLikes() {
        return likes;
    }

    public void setLikes(HashSet<Like> likes) {
        this.likes = likes;
    }

    public HashSet<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(HashSet<Photo> photos) {
        this.photos = photos;
    }

    public HashSet<UserStudentRoomRating> getUserStudentRoomRatings() {
        return userStudentRoomRatings;
    }

    public void setUserStudentRoomRatings(HashSet<UserStudentRoomRating> userStudentRoomRatings) {
        this.userStudentRoomRatings = userStudentRoomRatings;
    }
}
