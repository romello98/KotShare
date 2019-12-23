package com.example.kotshare.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;

class Country
{
    private Integer id;
    private String name;
    @SerializedName("City")
    private HashSet<City> cities;

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

    public HashSet<City> getCities() {
        return cities;
    }

    public void setCities(HashSet<City> cities) {
        this.cities = cities;
    }

    @NonNull
    @Override
    public String toString() {
        if(name == null) return "Pays inconnu";
        return name;
    }
}
