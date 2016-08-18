package com.example.androidpodcastplayer.model;


import com.google.gson.annotations.SerializedName;

public class Duration {

    @SerializedName("__text")
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
