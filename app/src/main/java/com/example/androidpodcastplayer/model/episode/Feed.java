package com.example.androidpodcastplayer.model.episode;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

import java.io.Serializable;


@Root(name = "rss", strict = false)
@Namespace(reference = "http://www.itunes.com/dtds/podcast-1.0.dtd")
public class Feed implements Serializable{

    @Element(name = "channel")
    private Channel channel;

    public Feed() {  }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

}
