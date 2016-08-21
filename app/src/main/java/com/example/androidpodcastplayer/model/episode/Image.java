package com.example.androidpodcastplayer.model.episode;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Root(strict = false)
public class Image implements Serializable{

    @Attribute(name = "href", required = false)
    private String url;

    public Image() {  }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
