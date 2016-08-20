package com.example.androidpodcastplayer.model.episode;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class EpisodeImage {

    @Attribute(name = "href", required = false)
    private String url;

    public EpisodeImage() {}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
