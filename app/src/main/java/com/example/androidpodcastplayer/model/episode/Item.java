package com.example.androidpodcastplayer.model.episode;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;


@Root(strict = false)
public class Item implements Parcelable {

    // @Element(name = "title", required = false)
    @Path("title")
    @Text(required = false)
    private String title;
    @Element(name = "pubDate", required = false)
    private String pubDate;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "duration", required = false)
    private String duration;
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
    @Path("subtitle")
    @Text(required = false)
    private String subtitle;
    @Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
    @Element(name = "image", required = false)
    private Image image;
    // @Path("enclosure")
    // @Text(required = false)
    @Element(name = "enclosure", required = false)
    private Enclosure enclosure;


    public Item() { }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
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
        dest.writeString(this.subtitle);
        dest.writeParcelable(this.image, flags);
        dest.writeParcelable(this.enclosure, flags);
    }

    protected Item(Parcel in) {
        this.title = in.readString();
        this.pubDate = in.readString();
        this.duration = in.readString();
        this.description = in.readString();
        this.author = in.readString();
        this.subtitle = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.enclosure = in.readParcelable(Enclosure.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
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
