package com.example.androidpodcastplayer.model;


import com.google.gson.annotations.SerializedName;

public class Description {

    @SerializedName("__cdata")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
