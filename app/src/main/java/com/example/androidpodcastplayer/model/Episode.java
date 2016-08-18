package com.example.androidpodcastplayer.model;

/**
{
    "title":"Episode #401 (Originally aired 07/29/16)",
    "pubDate":"Sun, 31 Jul 2016 17:47:28 +0000",
    "guid":{
        "_isPermaLink":"false",
        "__cdata":"79b5d7af27ade93b1588a4b64b438dec"
    },
    "link":{
        "__cdata":"http://billmaher.hbo.libsynpro.com/episode-401-originally-aired-072916-0"
    },
    "image":{
        "_href":"http://static.libsyn.com/p/assets/a/0/6/1/a061ceb8595319af/billmaher_logo1400.jpg",
        "__prefix":"itunes"
    },
        "description":{
        "__cdata":"<p>Episode #401 (Originally aired 07/29/16) - Bill’s guests are Bernie Sanders, Barney Frank, Alex Wagner, Matt Welch and Dr. Cornel West.</p>"
    },
    "enclosure":{
        "_length":"41180817",
        "_type":"audio/mpeg",
        "_url":"http://traffic.libsyn.com/billmaher/1469986131404_RTWBM_072916_EpisodeREVISED_target_format.mp3"
    },
    "duration":{
        "__prefix":"itunes",
        "__text":"57:12"
    },
        "explicit":{
        "__prefix":"itunes",
        "__text":"yes"
    },
    "keywords":{
        "__prefix":"itunes",
        "__text":"time,news,real,politics,bill,jokes,with,maher,overtimes"
    },
    "subtitle":{
        "__prefix":"itunes",
        "__cdata":"Episode #401 (Originally aired 07/29/16) - Bill’s guests are Bernie Sanders, Barney Frank, Alex Wagner, Matt Welch and Dr. Cornel West."
    }
}
*/


public class Episode {

    private String title;
    private String pubDate;
    private Image image;
    private Duration duration;
    private Subtitle subtitle;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Subtitle getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(Subtitle subtitle) {
        this.subtitle = subtitle;
    }
}
