package com.example.androidpodcastplayer.model.episode;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(strict = false)
public class Image implements Serializable{

    @Element(name = "url", required = false)
    private String url;

    @Element(name = "title", required = false)
    private String title;

    public Image() {  }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
