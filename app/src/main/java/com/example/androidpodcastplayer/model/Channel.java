package com.example.androidpodcastplayer.model;


import java.util.List;

public class Channel {

    private String title;
    private String pubDate;
    private String language;
    private List<Image> image;
    private Category category;
    private Description description;
    private List<Episode> item;

    public Channel() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public List<Episode> getItem() {
        return item;
    }

    public void setItem(List<Episode> item) {
        this.item = item;
    }


}
