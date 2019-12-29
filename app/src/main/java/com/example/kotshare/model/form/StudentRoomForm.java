package com.example.kotshare.model.form;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class StudentRoomForm
{
    private String title;
    private String description;
    private String street;
    private String streetNumber;
    private Double lat;
    @SerializedName("long")
    private Double longitude;
    private Integer monthlyPrice;
    private Boolean personnalBathroom;
    private Boolean personnalKitchen;
    private Boolean hasGarden;
    private Boolean freeWifi;
    private Integer numberRoommate;
    private Boolean isHidden;
    private Date startRentingDate;
    private Date endRentingDate;
    private Integer userId;
    private Integer cityId;

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

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(Integer monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
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

    public Date getEndRentingDate() {
        return endRentingDate;
    }

    public void setEndRentingDate(Date endRentingDate) {
        this.endRentingDate = endRentingDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Double getLong()
    {
        return longitude;
    }

    public void setLong(Double longitude)
    {
        this.longitude = longitude;
    }
}
