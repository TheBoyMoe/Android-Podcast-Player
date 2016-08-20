package com.example.androidpodcastplayer.model.episode;


import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import java.io.Serializable;
@Root(strict = false)
public class EpisodeInfo implements Serializable{

    @Attribute(name = "length", required = false)
    private String length;

    @Attribute(name = "type", required = false)
    private String type;

    @Attribute(name = "url", required = false)
    private String url;

    public EpisodeInfo() { }

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
