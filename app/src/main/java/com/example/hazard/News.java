package com.example.hazard;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {
    private int id;
    private String title;
    private String description;
    private String date;
    private String location;
    private String url;
    private String image;

    public News(int id, String title, String description, String date, String location, String url, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.url = url;
        this.image = image;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }



    public String getLocation() {
        return location;
    }



    public String getDate() {
        return date;
    }



    public String getDescription() {
        return description;
    }



    public String getUrl() {
        return url;
    }



    public String getImage() {
        return image;
    }



}