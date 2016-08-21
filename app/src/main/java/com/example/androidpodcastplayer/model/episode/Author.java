package com.example.androidpodcastplayer.model.episode;

import org.simpleframework.xml.Element;

import java.io.Serializable;


public class Author implements Serializable{

    @Element(name = "author", required = false)
    private String author;

    public Author() {}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
