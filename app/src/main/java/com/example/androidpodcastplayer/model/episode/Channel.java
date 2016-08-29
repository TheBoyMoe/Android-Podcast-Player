package com.example.androidpodcastplayer.model.episode;


import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

import java.util.List;

@Root(strict = false)
public class Channel implements Parcelable {

    // @Element(name = "title", required = false)
    @Path("title")
    @Text(required = false)
    private String title;
    @Element(name = "pubDate", required = false)
    private String pubDate;
    @Element(name = "lastBuildDate", required = false)
    private String lastBuildDate;
    @Element(name = "language", required = false)
    private String language;
    // @Element(name = "description", required = false)
    @Path("description")
    @Text(required = false)
    private String description;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    // @Element(name = "author", required = false)
    @Path("author")
    @Text(required = false)
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

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.pubDate);
        dest.writeString(this.lastBuildDate);
        dest.writeString(this.language);
        dest.writeString(this.description);
        dest.writeString(this.author);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.category);
        dest.writeTypedList(this.list);
    }

    protected Channel(Parcel in) {
        this.title = in.readString();
        this.pubDate = in.readString();
        this.lastBuildDate = in.readString();
        this.language = in.readString();
        this.description = in.readString();
        this.author = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.category = in.createTypedArrayList(Category.CREATOR);
        this.list = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Channel> CREATOR = new Parcelable.Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
