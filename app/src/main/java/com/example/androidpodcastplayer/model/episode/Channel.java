package com.example.androidpodcastplayer.model.episode;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(strict = false)
public class Channel implements Serializable{

    @Element(name = "title", required = false)
    private String title;
    @Element(name = "pubDate", required = false)
    private String pubDate;
    @Element(name = "lastBuildDate", required = false)
    private String buildDate;
    @Element(name = "language", required = false)
    private String language;
    @Element(name = "description", required = false)
    private String description;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "author", required = false)
    private String author;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @ElementList(name = "image", required = false, inline = true)
    private List<Image> images;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @ElementList(name = "category", required = false, inline = true)
    private List<Category> category;
    @ElementList(name = "item", required = false, inline = true)
    private List<Item> list;

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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItemList() {
        return list;
    }

    public void setItemList(List<Item> list) {
        this.list = list;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }
}
