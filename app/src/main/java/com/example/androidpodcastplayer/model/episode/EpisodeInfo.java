package com.example.androidpodcastplayer.model.episode;


import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
@Root(strict = false)
public class EpisodeInfo implements Parcelable {

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.length);
        dest.writeString(this.type);
        dest.writeString(this.url);
    }

    protected EpisodeInfo(Parcel in) {
        this.length = in.readString();
        this.type = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<EpisodeInfo> CREATOR = new Parcelable.Creator<EpisodeInfo>() {
        @Override
        public EpisodeInfo createFromParcel(Parcel source) {
            return new EpisodeInfo(source);
        }

        @Override
        public EpisodeInfo[] newArray(int size) {
            return new EpisodeInfo[size];
        }
    };
}
