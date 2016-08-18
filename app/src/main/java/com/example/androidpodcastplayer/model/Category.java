package com.example.androidpodcastplayer.model;


import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("_text")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
