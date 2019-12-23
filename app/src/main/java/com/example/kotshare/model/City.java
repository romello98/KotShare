package com.example.kotshare.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;

public class City
{
    private Integer id;
    private String name;
    private Country country;

    @SerializedName("StudentRoom")
    private HashSet<StudentRoom> studentRooms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public HashSet<StudentRoom> getStudentRooms() {
        return studentRooms;
    }

    public void setStudentRooms(HashSet<StudentRoom> studentRooms) {
        this.studentRooms = studentRooms;
    }

    @NonNull
    @Override
    public String toString() {
        if(name == null) return "Ville inconnue";
        if(country == null) return name;
        return name + ", " + country;
    }
}
