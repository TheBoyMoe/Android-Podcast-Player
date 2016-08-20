package com.example.androidpodcastplayer.model.episode;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.io.Serializable;


@Root(strict = false)
public class Episode implements Serializable {

    @Element(name = "title", required = false)
    private String title;
    @Element(name = "pubDate", required = false)
    private String pubDate;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "duration", required = false)
    private String duration;
    @Element(name = "description", required = false)
    private String description;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "author", required = false)
    private String author;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "image", required = false)
    private EpisodeImage image;
    @Element(name = "enclosure", required = false)
    private EpisodeInfo episodeInfo;


    public Episode() { }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public EpisodeImage getImage() {
        return image;
    }

    public void setImage(EpisodeImage image) {
        this.image = image;
    }

    public EpisodeInfo getEpisodeInfo() {
        return episodeInfo;
    }

    public void setEpisodeInfo(EpisodeInfo episodeInfo) {
        this.episodeInfo = episodeInfo;
    }


}
