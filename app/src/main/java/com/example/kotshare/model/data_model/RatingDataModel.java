package com.example.kotshare.model.data_model;

public class RatingDataModel
{
    private Integer userId;
    private Integer studentRoomId;
    private Float ratingValue;

    public RatingDataModel() {}

    public RatingDataModel(Integer userId, Integer studentRoomId, Float ratingValue) {
        setUserId(userId);
        setStudentRoomId(studentRoomId);
        setRatingValue(ratingValue);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStudentRoomId() {
        return studentRoomId;
    }

    public void setStudentRoomId(Integer studentRoomId) {
        this.studentRoomId = studentRoomId;
    }

    public Float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Float ratingValue) {
        this.ratingValue = ratingValue;
    }
}
