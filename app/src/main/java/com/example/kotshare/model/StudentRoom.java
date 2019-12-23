package com.example.kotshare.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.HashSet;

public class StudentRoom
{
    private Integer id;
    private String title;
    private String description;
    private Double monthlyPrice;
    @SerializedName("lat")
    private Double latitude;
    @SerializedName("long")
    private Double longitude;
    private Boolean personnalBathroom;
    private Boolean personnalKitchen;
    private Boolean hasGarden;
    private Boolean freeWifi;
    private Integer numberRoommate;
    private Boolean isHidden;
    private Date startRentingDate;
    private Date endRatingDate;
    private City city;
    private String street;
    private String streetNumber;
    private User user;
    private boolean isLiked;
    private HashSet<Like> likes = new HashSet<>();
    private HashSet<Photo> photos = new HashSet<>();
    private HashSet<UserStudentRoomRating> userStudentRoomRatings = new HashSet<>();

    public User getUser() {
        return user;
    }

    public StudentRoom() {}

    public StudentRoom(Integer id, String title, Double monthlyPrice, User user)
    {
        setId(id);
        setTitle(title);
        setMonthlyPrice(monthlyPrice);
        setUser(user);
        isLiked = false;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean getPersonnalBathroom() {
        return personnalBathroom;
    }

    public void setPersonnalBathroom(Boolean personnalBathroom) {
        this.personnalBathroom = personnalBathroom;
    }

    public Boolean getPersonnalKitchen() {
        return personnalKitchen;
    }

    public void setPersonnalKitchen(Boolean personnalKitchen) {
        this.personnalKitchen = personnalKitchen;
    }

    public Boolean getHasGarden() {
        return hasGarden;
    }

    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    public Boolean getFreeWifi() {
        return freeWifi;
    }

    public void setFreeWifi(Boolean freeWifi) {
        this.freeWifi = freeWifi;
    }

    public Integer getNumberRoommate() {
        return numberRoommate;
    }

    public void setNumberRoommate(Integer numberRoommate) {
        this.numberRoommate = numberRoommate;
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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
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

    public Double getLat() {
        return latitude;
    }

    public void setLat(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLong() {
        return longitude;
    }

    public void setLong(Double longitude) {
        this.longitude = longitude;
    }
}
