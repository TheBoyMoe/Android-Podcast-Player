package com.example.androidpodcastplayer.model;


import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName(value = "url", alternate = "_href")
    private String url;

    public Image() {  }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
