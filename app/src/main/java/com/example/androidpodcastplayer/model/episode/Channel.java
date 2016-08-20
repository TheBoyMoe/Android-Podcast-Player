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
    @Element(name = "language", required = false)
    private String language;
    @ElementList(name = "description", required = false, inline = true)
    private List<String> description;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "author", required = false)
    private String author;
    @ElementList(name = "image", required = false, inline = true)
    private List<Image> image;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @ElementList(name = "category", required = false, inline = true)
    private List<Category> category;
    @ElementList(name = "item", required = false, inline = true)
    private List<Episode> items;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Episode> getItems() {
        return items;
    }

    public void setItems(List<Episode> items) {
        this.items = items;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

}
