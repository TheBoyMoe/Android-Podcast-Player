package com.example.androidpodcastplayer.model.episode;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;


@Root(strict = false)
public class Item implements Parcelable {

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
    private Image image;
    @Element(name = "enclosure", required = false)
    private EpisodeInfo episodeInfo;


    public Item() { }

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

    public EpisodeInfo getEpisodeInfo() {
        return episodeInfo;
    }

    public void setEpisodeInfo(EpisodeInfo episodeInfo) {
        this.episodeInfo = episodeInfo;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.pubDate);
        dest.writeString(this.duration);
        dest.writeString(this.description);
        dest.writeString(this.author);
        dest.writeParcelable(this.image, flags);
        dest.writeParcelable(this.episodeInfo, flags);
    }

    protected Item(Parcel in) {
        this.title = in.readString();
        this.pubDate = in.readString();
        this.duration = in.readString();
        this.description = in.readString();
        this.author = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.episodeInfo = in.readParcelable(EpisodeInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
