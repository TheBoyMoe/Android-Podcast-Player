package com.example.androidpodcastplayer.model;


import com.google.gson.annotations.SerializedName;

public class Enclosure {

    @SerializedName("_length")
    private String length;

    @SerializedName("_type")
    private String type;

    @SerializedName("_url")
    private String url;

    public Enclosure() { }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
